package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.DefaultTreatmentDto;
import com.example.utapCattle.model.entity.DefaultTreatment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DefaultTreatmentMapper {
    DefaultTreatmentDto toDto(DefaultTreatment pen);

    DefaultTreatment toEntity(DefaultTreatmentDto defaultTreatmentDto);
}