package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.service.FarmService;
import com.example.utapCattle.service.repository.FarmRepository;

@Service
public class FarmServiceImpl implements FarmService {

    @Autowired
    private FarmRepository farmRepository;

    @Override
    public List<FarmDto> getAllFarms() {
        return farmRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FarmDto getFarmById(Long id) {
        Optional<Farm> farm = farmRepository.findById(id);
        return farm.map(this::mapToDto).orElse(null);
    }

    // Helper method to map Farm to FarmDto
    private FarmDto mapToDto(Farm farm) {
        return new FarmDto(
                farm.getFarmId(),
                farm.getFarmName(),
                farm.getFarmContact(),
                farm.getAddress(),
                farm.getHoldingNumber(),
                farm.getAssuranceNumber(),
                farm.getAssuranceExpiryDate(),
                farm.getCounty(),
                farm.getPostcode(),
                farm.getFarmRef(),
                farm.getCurrent()
        );
    }
}
