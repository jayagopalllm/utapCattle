package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.utapCattle.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // You can add custom query methods here if needed
}

