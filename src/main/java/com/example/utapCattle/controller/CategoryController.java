package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.CategoryDto;
import com.example.utapCattle.model.entity.Category;
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

    @PostMapping("/save") // Save a new category
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody Category category) {
        CategoryDto savedCategoryDto = categoryService.saveCategory(category);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }

    // Add more methods for creating, updating, and deleting categories if needed
}
