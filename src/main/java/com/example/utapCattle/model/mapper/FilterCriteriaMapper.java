package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.FilterCriteriaDto;
import com.example.utapCattle.model.entity.FilterCriteria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilterCriteriaMapper {
    FilterCriteriaDto toDto(FilterCriteria filterCriteria);

    FilterCriteria toEntity(FilterCriteriaDto filterCriteriaDto);
}
