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
        try {
            BreedDto breed = breedService.getBreedById(id);
            if (breed != null) {
                logger.info("Retrieved breed with ID: {}", id);
                return ResponseEntity.ok(breed);
            } else {
                logger.info("No breed found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve breed with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BreedDto>> getAllBreeds() {    
        try {
            List<BreedDto> breeds = breedService.getAllBreeds();
            logger.info("Retrieved {} breeds", breeds.size());
            return ResponseEntity.ok(breeds);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve all breeds", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<Long> saveBreed(@RequestBody Breed breed) {
        logger.info("Saving new breed: {}", breed);
        try {
            BreedDto savedBreedDto = breedService.saveBreed(breed);
            logger.info("Saved breed with ID: {}", savedBreedDto.getBreedid());
            return new ResponseEntity<>(savedBreedDto.getBreedid(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to save breed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
