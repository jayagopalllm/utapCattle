package com.example.utapCattle.controller;

import com.example.utapCattle.adminactions.conformationgrade.ConformationGrade;
import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController {

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
    public ResponseEntity<List<CustomerDto>> getAllCustomers(HttpServletRequest request) {
        try {
            Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
            List<CustomerDto> customers = customerService.getAllCustomers(userFarmId);
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

    @GetMapping("/API/{id}")
    public ResponseEntity<List<Object[]>> getCustomersApiData(@PathVariable("id") String id) {
        try {
            List<Object[]> customers = customerService.getCustomersApiData(id);
            logger.info("Retrieved {} customers", customers.size());
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable Long id, @RequestBody Customer condition) {
        try {
            return ResponseEntity.ok(customerService.update(id, condition));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            customerService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
