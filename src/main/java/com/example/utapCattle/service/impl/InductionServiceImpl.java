package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.InductionDto;
import com.example.utapCattle.model.entity.Induction;
import com.example.utapCattle.service.InductionService;
import com.example.utapCattle.service.repository.InductionRepository;

@Service
public class InductionServiceImpl implements InductionService {

	@Autowired
	private InductionRepository inductionRepository;

	@Override
	public List<InductionDto> getAllInductions() {
		return inductionRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public InductionDto getInductionById(Long id) {
		Optional<Induction> induction = inductionRepository.findById(id);
		return induction.map(this::mapToDto).orElse(null);
	}

	@Override
	public InductionDto saveInduction(Induction induction) {
		long nextInductionId = inductionRepository.getNextSequenceValue();
		induction.setId(nextInductionId);
		Induction savedInduction = inductionRepository.save(induction);
		return mapToDto(savedInduction);
	}

	private InductionDto mapToDto(Induction induction) {
		return new InductionDto(induction.getId(), induction.getBarcode(), induction.getEid(), induction.getEarTag(),
				induction.getChipNumber(), induction.getCondition());
	}
}
