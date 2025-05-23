package com.example.utapCattle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.exception.DuplicateCattleException;
import com.example.utapCattle.model.entity.CalfRegistry;
import com.example.utapCattle.service.repository.CalfRegistryRepository;

@Service
public class CalfRegistryService {

    @Autowired
    private CalfRegistryRepository repository;

    public CalfRegistry save(CalfRegistry calf) {
        String eartag= calf.getEartag();

        if (repository.existsByEartag(eartag)) {
            throw new DuplicateCattleException("Eartag " + eartag + " already registered.");
        }
        return repository.save(calf);
    }
}
