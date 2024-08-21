package com.example.utapCattle.service;

import com.example.utapCattle.model.dto.InductionDto;
import com.example.utapCattle.model.entity.Induction;

import java.util.List;

public interface InductionService {

	List<InductionDto> getAllInductions();

	InductionDto getInductionById(Long id);

	InductionDto saveInduction(Induction induction);
}
