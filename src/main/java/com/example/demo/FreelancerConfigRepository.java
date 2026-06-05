package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FreelancerConfigRepository extends JpaRepository<FreelancerConfig, Long> {
    Optional<FreelancerConfig> findByUser_Id(Long userId);
}
