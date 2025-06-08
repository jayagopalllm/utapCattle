package com.example.utapCattle.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.utapCattle.exception.DuplicateCattleException;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.model.entity.CalfRegistry;
import com.example.utapCattle.model.entity.Category;
import com.example.utapCattle.service.repository.BreedRepository;
import com.example.utapCattle.service.repository.CalfRegistryRepository;
import com.example.utapCattle.service.repository.CategoryRepository;

@Service
public class CalfRegistryService {

    private final CalfRegistryRepository repository;
    private final BreedRepository breedRepository;
    private final CategoryRepository categoryRepository;

    public CalfRegistryService(CalfRegistryRepository repository, BreedRepository breedRepository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.breedRepository = breedRepository;
        this.categoryRepository = categoryRepository;
    }

    public CalfRegistry save(CalfRegistry calf) {
        String eartag= calf.getEartag();

        if (repository.existsByEartag(eartag)) {
            throw new DuplicateCattleException("Eartag " + eartag + " already registered.");
        }
        return repository.save(calf);
    }

    public Map<String, List<?>> getOnloadData(Long userFarmId) {
        final List<Breed> breeds = breedRepository.findAll();
        final List<Category> categories = categoryRepository.findAll();

        return Map.of(
            "breeds", breeds,
            "categories", categories
        );
    }
}
