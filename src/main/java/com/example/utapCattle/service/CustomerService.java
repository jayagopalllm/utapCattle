package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.entity.Customer;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    CustomerDto saveCustomer(Customer customer);
}
