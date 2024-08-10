package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.Farm;

public interface FarmService {

    List<Farm> getAllFarms();

    Farm getFarmById(Long id);
}
