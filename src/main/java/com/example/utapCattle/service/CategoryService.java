// 1. CategoryService.java
package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.CategoryDto;

public interface CategoryService {

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    // Add more methods for creating, updating, and deleting categories if needed
}
