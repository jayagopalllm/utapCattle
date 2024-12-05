package com.example.utapCattle.service.impl;

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

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


        @Override
    public CategoryDto getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(this::mapToDto).orElse(null);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto saveCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    // Helper method to map Category to CategoryDto
    private CategoryDto mapToDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getCategoryDesc(),
                category.getSex()
        );
    }

    // Add more methods for creating, updating, and deleting categories if needed
}

