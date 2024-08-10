package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.Customer;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);
}
