package com.example.utapCattle.mapper;

import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
}
