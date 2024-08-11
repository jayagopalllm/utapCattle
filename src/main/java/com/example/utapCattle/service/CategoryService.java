// 1. CategoryService.java
package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.CategoryDto;
import com.example.utapCattle.model.entity.Category;

public interface CategoryService {

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto saveCategory(Category category);

    // Add more methods for creating, updating, and deleting categories if needed
}
