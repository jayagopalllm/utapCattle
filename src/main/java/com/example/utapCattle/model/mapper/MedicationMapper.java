package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.MedicationDto;
import com.example.utapCattle.model.entity.Medication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicationMapper {
    MedicationDto toDto(Medication medication);

    Medication toEntity(MedicationDto medicationDto);
}