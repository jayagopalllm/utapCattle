package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.FarmDto;

public interface FarmService {

    List<FarmDto> getAllFarms();

    FarmDto getFarmById(Long id);
}
