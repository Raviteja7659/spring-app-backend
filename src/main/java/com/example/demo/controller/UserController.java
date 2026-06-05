package com.example.demo.controller;

import com.example.demo.User;
import com.example.demo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateRequest request, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (request.getMonthlySalary() != null) {
            user.setMonthlySalary(request.getMonthlySalary());
        }
        
        userRepository.save(user);
        return ResponseEntity.ok("Profile updated successfully");
    }
    public static class ProfileUpdateRequest {
        private Double monthlySalary;

        public Double getMonthlySalary() { return monthlySalary; }
        public void setMonthlySalary(Double monthlySalary) { this.monthlySalary = monthlySalary; }
    }
}
