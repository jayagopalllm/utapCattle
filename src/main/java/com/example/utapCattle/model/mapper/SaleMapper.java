package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.model.entity.Sale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    SaleDto toDto(Sale sale);

    Sale toEntity(SaleDto saleDto);
}