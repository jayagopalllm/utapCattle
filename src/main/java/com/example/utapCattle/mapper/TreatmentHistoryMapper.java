package com.example.utapCattle.mapper;

import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.entity.TreatmentHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreatmentHistoryMapper {
    TreatmentHistoryDto toDto(TreatmentHistory treatmentHistory);
    TreatmentHistory toEntity(TreatmentHistoryDto treatmentHistoryDto);
}
