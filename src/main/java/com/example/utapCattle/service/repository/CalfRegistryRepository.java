package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.utapCattle.model.entity.CalfRegistry;

public interface CalfRegistryRepository extends JpaRepository<CalfRegistry, Long> {

    boolean existsByEartag(String eartag);
}
