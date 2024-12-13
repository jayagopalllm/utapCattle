package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.MedicalConditionDto;
import com.example.utapCattle.model.entity.MedicalCondition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalConditionMapper {
    MedicalConditionDto toDto(MedicalCondition medicalCondition);

    MedicalCondition toEntity(MedicalConditionDto medicalConditionDto);
}