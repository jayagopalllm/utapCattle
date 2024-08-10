package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.CategoryDto;
import com.example.utapCattle.service.CategoryService;

@RestController
@RequestMapping("/category")  // Base path for category endpoints
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")  // Get category by ID
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return (categoryDto != null) ? ResponseEntity.ok(categoryDto) : ResponseEntity.notFound().build();
    }

    @GetMapping  // Get all categories
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Add more methods for creating, updating, and deleting categories if needed
}
