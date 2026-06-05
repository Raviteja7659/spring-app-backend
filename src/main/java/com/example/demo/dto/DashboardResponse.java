package com.example.demo.dto;

import java.util.List;
import com.example.demo.Transaction;
import com.example.demo.Goal;
import com.example.demo.Invoice;
import java.util.Map;

public class DashboardResponse {
    private Double netWorth;
    private Double monthlyIncome;
    private Double expectedIncome;
    private Double outgoing;
    private Double emis;
    private Double rent;
    private Double pocketMoney;
    private Integer taxBufferFunded;
    private Integer taxBufferTarget;
    private Integer taxBufferCurrent;
    private List<Map<String, Object>> chartData;
    private List<Map<String, Object>> topCategories;
    private List<Transaction> transactions;
    private List<Goal> goals;
    private List<Invoice> invoices;

    // Getters and Setters omitted for brevity in DTO, using public fields or basic generation
    public Double getNetWorth() { return netWorth; }
    public void setNetWorth(Double netWorth) { this.netWorth = netWorth; }
    public Double getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(Double monthlyIncome) { this.monthlyIncome = monthlyIncome; }
    public Double getExpectedIncome() { return expectedIncome; }
    public void setExpectedIncome(Double expectedIncome) { this.expectedIncome = expectedIncome; }
    public Double getOutgoing() { return outgoing; }
    public void setOutgoing(Double outgoing) { this.outgoing = outgoing; }
    public Double getEmis() { return emis; }
    public void setEmis(Double emis) { this.emis = emis; }
    public Double getRent() { return rent; }
    public void setRent(Double rent) { this.rent = rent; }
    public Double getPocketMoney() { return pocketMoney; }
    public void setPocketMoney(Double pocketMoney) { this.pocketMoney = pocketMoney; }
    public Integer getTaxBufferFunded() { return taxBufferFunded; }
    public void setTaxBufferFunded(Integer taxBufferFunded) { this.taxBufferFunded = taxBufferFunded; }
    public Integer getTaxBufferTarget() { return taxBufferTarget; }
    public void setTaxBufferTarget(Integer taxBufferTarget) { this.taxBufferTarget = taxBufferTarget; }
    public Integer getTaxBufferCurrent() { return taxBufferCurrent; }
    public void setTaxBufferCurrent(Integer taxBufferCurrent) { this.taxBufferCurrent = taxBufferCurrent; }
    public List<Map<String, Object>> getChartData() { return chartData; }
    public void setChartData(List<Map<String, Object>> chartData) { this.chartData = chartData; }
    public List<Map<String, Object>> getTopCategories() { return topCategories; }
    public void setTopCategories(List<Map<String, Object>> topCategories) { this.topCategories = topCategories; }
    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
    public List<Goal> getGoals() { return goals; }
    public void setGoals(List<Goal> goals) { this.goals = goals; }
    public List<Invoice> getInvoices() { return invoices; }
    public void setInvoices(List<Invoice> invoices) { this.invoices = invoices; }
}
