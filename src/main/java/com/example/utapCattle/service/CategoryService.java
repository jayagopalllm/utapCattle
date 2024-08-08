// 1. CategoryService.java
package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.Category;

public interface CategoryService {

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    // Add more methods for creating, updating, and deleting categories if needed
}
