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
        try {
            CategoryDto categoryDto = categoryService.getCategoryById(id);
            if (categoryDto != null) {
                logger.info("Retrieved category with ID: {}", id);
                return ResponseEntity.ok(categoryDto);
            } else {
                logger.warn("No Category found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve category with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        try {
            List<CategoryDto> categories = categoryService.getAllCategories();
            logger.info("Retrieved {} categories", categories.size());
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve all categories", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveCategory(@RequestBody Category category) {
        logger.info("Saving new category: {}", category);
        try {
            CategoryDto savedCategoryDto = categoryService.saveCategory(category);
            logger.info("Saved category with ID: {}", savedCategoryDto.getCategoryId()); // Assuming CategoryDto has getCategoryId()
            return new ResponseEntity<>(savedCategoryDto.getCategoryId(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to save category", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
