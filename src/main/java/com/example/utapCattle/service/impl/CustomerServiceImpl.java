package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.service.CustomerService;
import com.example.utapCattle.service.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

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
