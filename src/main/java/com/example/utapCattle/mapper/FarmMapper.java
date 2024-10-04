package com.example.utapCattle.mapper;

import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.model.entity.Farm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    FarmDto toDto(Farm farm);
    Farm toEntity(FarmDto farmDto);
}
