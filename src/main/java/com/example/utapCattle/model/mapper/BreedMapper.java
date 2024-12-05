package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.BreedDto;
import com.example.utapCattle.model.entity.Breed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BreedMapper {
    BreedDto toDto(Breed agent);

    Breed toEntity(BreedDto agentDto);
}