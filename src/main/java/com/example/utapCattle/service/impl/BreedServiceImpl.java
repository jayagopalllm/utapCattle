package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.BreedDto;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.service.BreedService;
import com.example.utapCattle.service.repository.BreedRepository;


@Service
public class BreedServiceImpl implements BreedService{


    @Autowired
    private BreedRepository breedRepository; 


    @Override
    public List<BreedDto> getAllBreeds() {
        return breedRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BreedDto getBreedById(Long id) {
        Optional<Breed> breed = breedRepository.findById(id);
        return breed.map(this::mapToDto).orElse(null);
    }

    @Override
    public BreedDto saveBreed(Breed breed) {
        Breed savedBreed = breedRepository.save(breed);
        return mapToDto(savedBreed);
    }

    // Helper method to map Breed to BreedDto
    private BreedDto mapToDto(Breed breed) {
        return new BreedDto(
                breed.getBreedid(),
                breed.getBreeddesc(),
                breed.getBreedabbr(),
                breed.getBreedfull(),
                breed.getBreedcatego(),
                breed.getBeefdairy()
        );
    }

}
