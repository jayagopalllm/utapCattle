package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.Cattle; // Import the Cattle model

public interface CattleService {

    List<Cattle> getAllCattle();

    Cattle getCattleById(Long id); // Use Long as the ID type
}
