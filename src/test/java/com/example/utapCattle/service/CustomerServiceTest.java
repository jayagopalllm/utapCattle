package com.example.utapCattle.service;

import com.example.utapCattle.mapper.CustomerMapper;
import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.service.impl.CustomerServiceImpl;
import com.example.utapCattle.service.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    private CustomerService customerService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, mapper);
    }

    @Test
    public void testGetAllCustomers_WhenCustomerExists_ShouldReturnAllCustomers() {
        List<CustomerDto> customerDtoList = Arrays.asList(new CustomerDto().builder()
                        .customerId(1L)
                        .customerName("person1").build(),
                new CustomerDto().builder()
                        .customerId(1L)
                        .customerName("person1").build());

        List<Customer> customerList = customerDtoList.stream().map(mapper::toEntity).collect(Collectors.toList());

        when(customerRepository.findAll()).thenReturn(customerList);
        List<CustomerDto> result = customerService.getAllCustomers();
        assertEquals(result, customerDtoList);
    }

    @Test
    public void testGetCustomerById_WhenCustomerExists_ShouldReturnCustomer() {
        CustomerDto customerDto = new CustomerDto().builder()
                .customerId(1L)
                .customerName("person1").build();
        Customer customer = mapper.toEntity(customerDto);

        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        CustomerDto result = customerService.getCustomerById(1L);
        assertEquals(result, customerDto);
    }

    @Test
    public void testSaveCustomer_WhenCustomerIsSaved_ShouldReturnSavedCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto().builder()
                .customerId(1L)
                .customerName("person1").build();
        Customer customer = mapper.toEntity(customerDto);

        when(customerRepository.save(customer)).thenReturn(customer);
        CustomerDto result = customerService.saveCustomer(customer);
        assertEquals(result, customerDto);
    }
}
