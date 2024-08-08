package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.Breed;

public interface BreedService {

        List<Breed> getAllBreeds();

        Breed getBreedById(Long id);
}

