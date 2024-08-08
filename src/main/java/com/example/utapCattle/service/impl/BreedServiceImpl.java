package com.example.utapCattle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.Breed;
import com.example.utapCattle.service.BreedService;
import com.example.utapCattle.service.repository.BreedRepository;


@Service
public class BreedServiceImpl implements BreedService{


    @Autowired
    private BreedRepository breedRepository; // Inject the AgentRepository


    @Override
    public List<Breed> getAllBreeds() {
        return breedRepository.findAll();
    }

    @Override
    public Breed getBreedById(Long id) {
        return breedRepository.findById(id).orElse(null);
    }

}
