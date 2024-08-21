package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;

public interface CattleService {

	List<CattleDto> getAllCattle();

	CattleDto getCattleById(Long id); // Use Long as the ID type

	CattleDto saveCattle(Cattle cattle) throws Exception;

	List<String> getEartags();
}
