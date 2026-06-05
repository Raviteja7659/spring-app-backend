package com.example.demo.controller;

import com.example.demo.Transaction;
import com.example.demo.TransactionRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.security.Principal;
import com.example.demo.UserRepository;
import com.example.demo.User;
import com.example.demo.AccountRepository;
import com.example.demo.CategoryRepository;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    public TransactionController(TransactionRepository transactionRepository, 
                                 UserRepository userRepository,
                                 AccountRepository accountRepository,
                                 CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequest request, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Transaction tx = new Transaction();
        tx.setDate(LocalDate.now());
        tx.setMerchant(request.getDescription());
        tx.setAmount(request.getType().equalsIgnoreCase("withdrawal") ? -request.getAmount() : request.getAmount());
        tx.setCategory(request.getCategory());
        tx.setAccount(request.getAccount());
        tx.setUser(user);
        transactionRepository.save(tx);
        return ResponseEntity.ok("Transaction added successfully");
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<String>> getAccounts(Principal principal) {

        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<String> accounts = accountRepository.findByUser_Id(user.getId()).stream().map(a -> a.getName()).collect(Collectors.toList());
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories(Principal principal) {

        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<String> categories = categoryRepository.findByUser_Id(user.getId()).stream().map(c -> c.getName()).collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }
}

class TransactionRequest {
    private String description;
    private double amount;
    private String category;
    private String account;
    private String type; // withdrawal, deposit

    // Getters and Setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
