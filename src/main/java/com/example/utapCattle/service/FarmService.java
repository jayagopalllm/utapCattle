package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.model.entity.Farm;

public interface FarmService {

    List<FarmDto> getAllFarms();

    FarmDto getFarmById(Long id);

    FarmDto saveFarm(Farm farm);

    FarmDto update(Long id, Farm condition);

    void delete(Long id);

}
