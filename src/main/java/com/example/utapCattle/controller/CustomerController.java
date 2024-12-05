package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController{

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        try {
            CustomerDto customerDto = customerService.getCustomerById(id);
            if (customerDto != null) {
                logger.info("Request successful: Retrieved customer with ID: {}", id);
                return ResponseEntity.ok(customerDto);
            } else {
                logger.warn("Request failed: Customer not found for ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve customer with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        try {
            List<CustomerDto> customers = customerService.getAllCustomers();
            logger.info("Retrieved {} customers", customers.size());
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveCustomer(@RequestBody Customer customer) {
        logger.info("Saving new customer: {}", customer);
        try {
            CustomerDto savedCustomerDto = customerService.saveCustomer(customer);
            logger.info("Saved customer with ID: {}", savedCustomerDto.getCustomerId());
            return new ResponseEntity<>(savedCustomerDto.getCustomerId(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to save customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
