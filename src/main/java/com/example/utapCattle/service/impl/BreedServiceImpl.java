package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.BreedDto;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.service.BreedService;
import com.example.utapCattle.service.repository.BreedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BreedServiceImpl implements BreedService {

    private final BreedRepository breedRepository;

    public BreedServiceImpl(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

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
                breed.getBeefdairy());
    }

    @Override
    public BreedDto update(Long id, Breed condition) {
        return breedRepository.findById(id).map(existingCondition -> {
            existingCondition.setBreeddesc(condition.getBreeddesc());
            existingCondition.setBreedabbr(condition.getBreedabbr());
            existingCondition.setBreedfull(condition.getBreedfull());
            existingCondition.setBreedcatego(condition.getBreedcatego());
            existingCondition.setBeefdairy(condition.getBeefdairy());
            breedRepository.save(existingCondition);
            return mapToDto(existingCondition);
        }).orElseThrow(() -> new RuntimeException("Breed not found"));
    }

    @Override
    public void delete(Long id) {
        if (breedRepository.existsById(id)) {
            breedRepository.deleteById(id);
        } else {
            throw new RuntimeException("Breed not found");
        }
    }

}
