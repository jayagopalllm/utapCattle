package com.example.utapCattle.service.impl;

import com.example.utapCattle.mapper.CustomerMapper;
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
    private final CustomerMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(mapper::toDto).orElse(null);
    }

    @Override
    public CustomerDto saveCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return mapper.toDto(savedCustomer);
    }
}
