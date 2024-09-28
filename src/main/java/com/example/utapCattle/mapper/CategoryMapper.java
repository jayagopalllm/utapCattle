package com.example.utapCattle.mapper;

import com.example.utapCattle.model.dto.CategoryDto;
import com.example.utapCattle.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
}
