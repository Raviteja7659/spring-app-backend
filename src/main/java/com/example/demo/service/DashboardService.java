package com.example.demo.service;

import com.example.demo.GoalRepository;
import com.example.demo.InvoiceRepository;
import com.example.demo.TransactionRepository;
import com.example.demo.Transaction;
import com.example.demo.dto.DashboardResponse;
import com.example.demo.FreelancerConfig;
import com.example.demo.FreelancerConfigRepository;
import com.example.demo.User;
import com.example.demo.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class DashboardService {

    private final TransactionRepository transactionRepository;
    private final GoalRepository goalRepository;
    private final InvoiceRepository invoiceRepository;
    private final FreelancerConfigRepository freelancerConfigRepository;
    private final UserRepository userRepository;

    public DashboardService(TransactionRepository transactionRepository, GoalRepository goalRepository, InvoiceRepository invoiceRepository, FreelancerConfigRepository freelancerConfigRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.goalRepository = goalRepository;
        this.invoiceRepository = invoiceRepository;
        this.freelancerConfigRepository = freelancerConfigRepository;
        this.userRepository = userRepository;
    }

    public DashboardResponse getDashboardData(Long userId) {
        DashboardResponse response = new DashboardResponse();
        Optional<FreelancerConfig> configOpt = freelancerConfigRepository.findByUser_Id(userId);
        
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        response.setExpectedIncome(user.getMonthlySalary() != null ? user.getMonthlySalary() : 0.0);
        
        List<Transaction> transactions = transactionRepository.findByUser_Id(userId);
        response.setTransactions(transactions);
        
        double monthlyIncome = 0;
        double monthlyOutgoing = 0;
        double netWorth = 0; // Simple calc: income - outgoing
        
        LocalDate now = LocalDate.now();
        Map<String, Double> spendByMonth = new HashMap<>();
        Map<String, Double> categorySpending = new HashMap<>();

        for (Transaction tx : transactions) {
            double amt = tx.getAmount();
            netWorth += amt; // Positive for deposit, negative for withdrawal
            
            if (tx.getDate() != null) {
                String monthKey = tx.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                if (amt < 0) {
                    spendByMonth.put(monthKey, spendByMonth.getOrDefault(monthKey, 0.0) + Math.abs(amt));
                }
                
                if (tx.getDate().getMonth() == now.getMonth() && tx.getDate().getYear() == now.getYear()) {
                    if (amt > 0) monthlyIncome += amt;
                    if (amt < 0) {
                        monthlyOutgoing += Math.abs(amt);
                        String cat = tx.getCategory() != null ? tx.getCategory() : "Uncategorized";
                        categorySpending.put(cat, categorySpending.getOrDefault(cat, 0.0) + Math.abs(amt));
                    }
                }
            }
        }
        
        response.setNetWorth(netWorth > 0 ? netWorth : 0);
        response.setMonthlyIncome(monthlyIncome);
        response.setOutgoing(monthlyOutgoing);
        response.setEmis(0.0);
        response.setRent(1800.0); // Kept static for mock
        response.setPocketMoney(620.0); // Kept static for mock

        if (configOpt.isPresent()) {
            FreelancerConfig config = configOpt.get();
            response.setTaxBufferTarget(config.getTaxBufferTarget().intValue());
            response.setTaxBufferCurrent(config.getTaxBufferCurrent().intValue());
            response.setTaxBufferFunded((int)((config.getTaxBufferCurrent() / config.getTaxBufferTarget()) * 100));
        } else {
            response.setTaxBufferTarget(5000);
            response.setTaxBufferCurrent(0);
            response.setTaxBufferFunded(0);
        }

        List<Map<String, Object>> chartData = new java.util.ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            LocalDate m = now.minusMonths(i);
            String mKey = m.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            double amt = spendByMonth.getOrDefault(mKey, 0.0);
            chartData.add(Map.of(
                "month", mKey,
                "onBudget", amt,
                "overBudget", 0
            ));
        }
        response.setChartData(chartData);

        List<Map<String, Object>> topCategories = new java.util.ArrayList<>();
        final double finalMonthlyOutgoing = monthlyOutgoing;
        categorySpending.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(5)
            .forEach(e -> {
                topCategories.add(Map.of(
                    "name", e.getKey(),
                    "amount", e.getValue(),
                    "icon", "card",
                    "progress", finalMonthlyOutgoing > 0 ? (e.getValue() / finalMonthlyOutgoing * 100) : 0
                ));
            });
            
        if (topCategories.isEmpty()) {
            // Default mock categories if no spending
            topCategories.addAll(Arrays.asList(
                Map.of("name", "Housing", "amount", 1800.0, "icon", "house", "progress", 60),
                Map.of("name", "Food & Dining", "amount", 620.0, "icon", "bowl", "progress", 40)
            ));
        }
        response.setTopCategories(topCategories);

        response.setGoals(goalRepository.findByUser_Id(userId));
        response.setInvoices(invoiceRepository.findByUser_Id(userId));

        return response;
    }
}
