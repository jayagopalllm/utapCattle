package com.example.utapCattle.mapper;

import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.Market;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarketMapper {
    MarketDto toDto(Market market);
    Market toEntity(MarketDto marketDto);
}
