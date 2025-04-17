package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.service.CustomerService;
import com.example.utapCattle.service.UserService;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CattleRepository cattleRepository;
    private final UserService userService ;

    public CustomerServiceImpl(CustomerRepository customerRepository, CattleRepository cattleRepository,UserService userService) {
        this.customerRepository = customerRepository;
        this.cattleRepository = cattleRepository;
        this.userService = userService;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getCustomersApiData(final String id) {
        return cattleRepository.findCattleDataById(id);
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
                customer.getCustomerName());
    }
}
