package com.example.utapCattle.service.impl;

import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.ScaleWeightDto;
import com.example.utapCattle.service.ScaleWeightService;

@Service
public class ScaleWeightServiceImpl implements ScaleWeightService {

    @Override
    public ScaleWeightDto saveWeight(ScaleWeightDto scaleWeightDto) {
        return scaleWeightDto; 
    }
}
