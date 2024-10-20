package com.example.utapCattle.mapper;

import com.example.utapCattle.model.dto.TbTestHistoryDto;
import com.example.utapCattle.model.entity.TbTestHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TbTestHistoryMapper {
    TbTestHistoryDto toDto(TbTestHistory tbTestHistory);
    TbTestHistory toEntity(TbTestHistoryDto tbTestHistoryDto);
}
