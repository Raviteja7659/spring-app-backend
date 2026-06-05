package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    public String getAIResponse(String message) {
        String msg = message.toLowerCase();
        if (msg.contains("tax") || msg.contains("taxes") || msg.contains("80c")) {
            return "For your Section 80C tax savings, you are currently 30% funded. You have invested ₹45,000 but should aim for the full ₹150,000 limit before March. I recommend moving ₹15,000 from your recent salary deposit into your ELSS Mutual Fund.";
        } else if (msg.contains("spend") || msg.contains("spending")) {
            return "Your spending this month is ₹45,500, which is normal for your budget. The spike to ₹65,000 in March was due to your annual insurance premium. Your top discretionary spending is Food (Swiggy).";
        } else if (msg.contains("invoice") || msg.contains("unpaid") || msg.contains("due") || msg.contains("emi")) {
            return "You have 1 overdue Personal Loan EMI for ₹12,000. You also have an HDFC Credit Card bill of ₹24,500 coming up in 5 days. Make sure to keep enough balance in your salary account!";
        } else {
            return "I'm your AI financial assistant powered by Claude. I have full context of your salary, EMIs, and investments. You can ask me about your 80C taxes, Swiggy spending, or pending bills!";
        }
    }
}
