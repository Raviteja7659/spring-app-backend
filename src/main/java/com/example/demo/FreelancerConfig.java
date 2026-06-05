package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "freelancer_config")
public class FreelancerConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double taxBufferTarget;
    private Double taxBufferCurrent;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public FreelancerConfig() {}
    public FreelancerConfig(Double taxBufferTarget, Double taxBufferCurrent, User user) {
        this.taxBufferTarget = taxBufferTarget;
        this.taxBufferCurrent = taxBufferCurrent;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getTaxBufferTarget() { return taxBufferTarget; }
    public void setTaxBufferTarget(Double taxBufferTarget) { this.taxBufferTarget = taxBufferTarget; }
    public Double getTaxBufferCurrent() { return taxBufferCurrent; }
    public void setTaxBufferCurrent(Double taxBufferCurrent) { this.taxBufferCurrent = taxBufferCurrent; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
