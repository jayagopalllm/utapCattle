package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.BreedDto;
import com.example.utapCattle.model.entity.Breed;

public interface BreedService {

        List<BreedDto> getAllBreeds();

        BreedDto getBreedById(Long id);

        BreedDto saveBreed(Breed breed);

        BreedDto update(Long id, Breed condition);

        void delete(Long id);
}
