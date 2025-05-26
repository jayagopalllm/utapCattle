package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.entity.Customer;

public interface CustomerService {

    List<CustomerDto> getAllCustomers(Long userFarmId);

    List<Object[]> getCustomersApiData(final String id);

    CustomerDto getCustomerById(Long id);

    CustomerDto saveCustomer(Customer customer);

    CustomerDto update(Long id, Customer condition);

    void delete(Long id);

}
