package com.example.utapCattle.service.impl;

import com.example.utapCattle.mapper.CategoryMapper;
import com.example.utapCattle.model.dto.CategoryDto;
import com.example.utapCattle.model.entity.Category;
import com.example.utapCattle.service.CategoryService;
import com.example.utapCattle.service.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

        @Override
    public CategoryDto getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(mapper::toDto).orElse(null);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto saveCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return mapper.toDto(savedCategory);
    }
}

