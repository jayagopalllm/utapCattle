package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;

public interface CattleService {

	List<CattleDto> getAllCattle();

	CattleDto getCattleById(final Long id); // Use Long as the ID type

	CattleDto getCattleByEarTag(final String earTag);

	CattleDto saveCattle(final Cattle cattle) throws Exception;

	CattleDto updateCattle(final Long id,final Cattle cattle) throws Exception;

	List<String> findEarTagsWithIncompleteInduction();
}
