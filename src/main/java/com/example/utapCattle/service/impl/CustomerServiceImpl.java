package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.service.CustomerService;
import com.example.utapCattle.service.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(this::mapToDto).orElse(null);
    }

    @Override
    public CustomerDto saveCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return mapToDto(savedCustomer);
    }

    // Helper method to map Customer to CustomerDto
    private CustomerDto mapToDto(Customer customer) {
        return new CustomerDto(
                customer.getCustomerId(),
                customer.getCustomerName()
        );
    }
}
