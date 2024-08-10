package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.BreedDto;

public interface BreedService {

        List<BreedDto> getAllBreeds();

        BreedDto getBreedById(Long id);
}

