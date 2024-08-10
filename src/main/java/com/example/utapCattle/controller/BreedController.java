package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.BreedDto;
import com.example.utapCattle.service.BreedService;

@RestController
@RequestMapping("/breed")  // Base path for agent endpoints
public class BreedController {

     @Autowired
    private BreedService breedService;

    @GetMapping("/{id}")  // Get agent by ID
    public ResponseEntity<BreedDto> getBreedById(@PathVariable Long id) {
        BreedDto breed = breedService.getBreedById(id);
        return (breed != null) ? ResponseEntity.ok(breed) : ResponseEntity.notFound().build();
    }

    @GetMapping  // Get all agents
    public List<BreedDto> getAllBreeds() {
        return breedService.getAllBreeds();
    }
    
}
