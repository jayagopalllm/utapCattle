package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController{

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        logger.info("Incoming request: Retrieving customer with ID: {}", id);
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            logger.info("Request successful: Retrieved customer with ID: {}", id);
            return ResponseEntity.ok(customer);
        } else {
            logger.warn("Request failed: Customer not found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        logger.info("Incoming request: Retrieving all customers");
        List<Customer> customers = customerService.getAllCustomers();
        logger.info("Request successful: Retrieved {} customers", customers.size());
        return customers;
    }

    @PostMapping("/save")
    public ResponseEntity<CustomerDto> saveCustomer(@RequestBody Customer customer) {
        logger.info("Incoming request: Saving new customer: {}", customer);
        CustomerDto savedCustomerDto = customerService.saveCustomer(customer);
        logger.info("Request successful: Saved customer with ID: {}", savedCustomerDto.getCustomerId()); // Assuming CustomerDto has getCustomerId()
        return new ResponseEntity<>(savedCustomerDto, HttpStatus.CREATED);
    }
}
