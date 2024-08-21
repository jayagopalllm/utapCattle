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

import com.example.utapCattle.model.dto.BreedDto;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.service.BreedService;

@RestController
@RequestMapping("/breed")
public class BreedController extends BaseController {

     @Autowired
    private BreedService breedService;

    @GetMapping("/{id}")
    public ResponseEntity<BreedDto> getBreedById(@PathVariable Long id) {
        logger.info("Incoming request: Retrieving breed with ID: {}", id);
        BreedDto breed = breedService.getBreedById(id);
        if (breed != null) {
            logger.info("Request successful: Retrieved breed with ID: {}", id);
            return ResponseEntity.ok(breed);
        } else {
            logger.warn("Request failed: Breed not found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<BreedDto> getAllBreeds() {
        logger.info("Incoming request: Retrieving all breeds");
        List<BreedDto> breeds = breedService.getAllBreeds();
        logger.info("Request successful: Retrieved {} breeds", breeds.size());
        return breeds;
    }
    
    @PostMapping("/save")
    public ResponseEntity<BreedDto> saveBreed(@RequestBody Breed breed) {
        logger.info("Incoming request: Saving new breed: {}", breed);
        BreedDto savedBreedDto = breedService.saveBreed(breed);
        logger.info("Request successful: Saved breed with ID: {}", savedBreedDto.getBreedid());
        return new ResponseEntity<>(savedBreedDto, HttpStatus.CREATED);
    }
}
