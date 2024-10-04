package com.example.utapCattle.mapper;

import com.example.utapCattle.model.dto.PenDto;
import com.example.utapCattle.model.entity.Pen;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PenMapper {
    PenDto toDto(Pen pen);
    Pen toEntity(PenDto penDto);
}
