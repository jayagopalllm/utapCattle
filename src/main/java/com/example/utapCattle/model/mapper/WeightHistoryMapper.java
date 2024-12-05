package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.WeightHistoryDto;
import com.example.utapCattle.model.entity.WeightHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeightHistoryMapper {

    WeightHistoryDto toDto(WeightHistory weightHistory);

    WeightHistory toEntity(WeightHistoryDto weightHistoryDto);
}
