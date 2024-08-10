package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.Customer;
import com.example.utapCattle.service.CustomerService;

@RestController
@RequestMapping("/customer") // Base path for customer endpoints
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}") // Get customer by ID
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return (customer != null) ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @GetMapping // Get all customers
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
