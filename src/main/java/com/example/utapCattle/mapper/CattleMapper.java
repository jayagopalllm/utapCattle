package com.example.utapCattle.mapper;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CattleMapper {
    CattleDto toDto(Cattle cattle);
    Cattle toEntity(CattleDto cattleDto);
}
