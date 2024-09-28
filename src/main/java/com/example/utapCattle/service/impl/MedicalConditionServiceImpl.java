package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.MedicalConditionDto;
import com.example.utapCattle.model.entity.MedicalCondition;
import com.example.utapCattle.service.MedicalConditionService;
import com.example.utapCattle.service.repository.MedicalConditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalConditionServiceImpl implements MedicalConditionService {

	private final MedicalConditionRepository medicalConditionRepository;

	public MedicalConditionServiceImpl(MedicalConditionRepository medicalConditionRepository) {
		this.medicalConditionRepository = medicalConditionRepository;
	}

	@Override
	public List<MedicalConditionDto> getAllMedicalCondition() {
		return medicalConditionRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	private MedicalConditionDto mapToDto(MedicalCondition medicalCond) {
		return new MedicalConditionDto(medicalCond.getMedicalConditionId(), medicalCond.getConditionDesc());
	}

}
