package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.Cattle;
import com.example.utapCattle.service.CattleService;
import com.example.utapCattle.service.repository.CattleRepository;

@Service
public class CattleServiceImpl implements CattleService {

    @Autowired
    private CattleRepository cattleRepository;

    @Override
    public List<Cattle> getAllCattle() {
        return cattleRepository.findAll();
    }

    @Override
    public Cattle getCattleById(Long id) {
        Optional<Cattle> cattle = cattleRepository.findById(id);
        return cattle.orElse(null); // Return null if not found
    }
}
