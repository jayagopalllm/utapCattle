package com.example.utapCattle.service;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;

import java.util.List;

public interface CattleService {

    List<CattleDto> getAllCattle();

    CattleDto getCattleById(Long id); // Use Long as the ID type

    CattleDto saveCattle(Cattle cattle);
}
