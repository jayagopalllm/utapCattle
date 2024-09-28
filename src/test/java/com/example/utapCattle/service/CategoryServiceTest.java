package com.example.utapCattle.service;

import com.example.utapCattle.mapper.CategoryMapper;
import com.example.utapCattle.model.dto.CategoryDto;
import com.example.utapCattle.model.entity.Category;
import com.example.utapCattle.service.impl.CategoryServiceImpl;
import com.example.utapCattle.service.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    public void fetchAllCategory_WhenCategoryExists_ShouldReturnAllCategory() {
        Category category = new Category(1L, "Heifer", "F");
        List<Category> categoryList = Arrays.asList(category);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<CategoryDto> result = categoryService.getAllCategories();
        assertEquals(result.size(), categoryList.size());
    }

    @Test
    public void fetchCategoryById_WhenCategoryExists_ShouldReturnCategory() {
        Category category = new Category(1L, "Heifer", "F");
        CategoryDto categoryDto = categoryMapper.toDto(category);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryDto result = categoryService.getCategoryById(1L);
        assertEquals(result, categoryDto);
    }

    @Test
    public void fetchSavedCategory_WhenCategoryIsSaved_ShouldReturnSavedCategory() {
        Category category = new Category(1L, "Heifer", "F");
        CategoryDto categoryDto = categoryMapper.toDto(category);

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDto result = categoryService.saveCategory(category);
        assertEquals(result, categoryDto);
    }
}
