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
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        logger.info("Incoming request: Retrieving category with ID: {}", id);
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (categoryDto != null) {
            logger.info("Request successful: Retrieved category with ID: {}", id);
            return ResponseEntity.ok(categoryDto);
        } else {
            logger.warn("Request failed: Category not found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        logger.info("Incoming request: Retrieving all categories");
        List<CategoryDto> categories = categoryService.getAllCategories();
        logger.info("Request successful: Retrieved {} categories", categories.size());
        return categories;
    }

    @PostMapping("/save")
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody Category category) {
        logger.info("Incoming request: Saving new category: {}", category);
        CategoryDto savedCategoryDto = categoryService.saveCategory(category);
        logger.info("Request successful: Saved category with ID: {}", savedCategoryDto.getCategoryId()); // Assuming CategoryDto has getCategoryId()
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }

}
