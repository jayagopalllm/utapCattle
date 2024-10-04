package com.example.utapCattle.service.impl;

import com.example.utapCattle.mapper.FarmMapper;
import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.service.FarmService;
import com.example.utapCattle.service.repository.FarmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;

    private final FarmMapper mapper;

    public FarmServiceImpl(FarmRepository farmRepository
            , FarmMapper mapper) {
        this.farmRepository = farmRepository;
        this.mapper =  mapper;
    }

    @Override
    public List<FarmDto> getAllFarms() {
        return farmRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FarmDto getFarmById(Long id) {
        Optional<Farm> farm = farmRepository.findById(id);
        return farm.map(mapper::toDto).orElse(null);
    }

    @Override
    public FarmDto saveFarm(Farm farm) {
        Farm savedFarm = farmRepository.save(farm);
        return mapper.toDto(savedFarm);
    }
}
