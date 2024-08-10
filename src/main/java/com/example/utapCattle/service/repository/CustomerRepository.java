package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.utapCattle.model.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // You can add custom query methods here if needed
}
