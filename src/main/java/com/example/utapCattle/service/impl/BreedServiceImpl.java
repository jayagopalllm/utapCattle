package com.example.utapCattle.service.impl;

import com.example.utapCattle.mapper.BreedMapper;
import com.example.utapCattle.model.dto.BreedDto;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.service.BreedService;
import com.example.utapCattle.service.repository.BreedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BreedServiceImpl implements BreedService{

    private final BreedRepository breedRepository;
    private final BreedMapper mapper;

    public BreedServiceImpl(BreedRepository breedRepository,
                            BreedMapper mapper) {
        this.breedRepository = breedRepository;
        this.mapper = mapper;
    }

    @Override
    public List<BreedDto> getAllBreeds() {
        return breedRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BreedDto getBreedById(Long id) {
        Optional<Breed> breed = breedRepository.findById(id);
        return breed.map(mapper::toDto).orElse(null);
    }

    @Override
    public BreedDto saveBreed(Breed breed) {
        Breed savedBreed = breedRepository.save(breed);
        return mapper.toDto(savedBreed);
    }

}
