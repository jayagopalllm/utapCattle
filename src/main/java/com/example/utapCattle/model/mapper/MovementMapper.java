package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.MovementDto;
import com.example.utapCattle.model.entity.Movement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementMapper {
    MovementDto toDto(Movement movement);

    Movement toEntity(MovementDto movementDto);
}