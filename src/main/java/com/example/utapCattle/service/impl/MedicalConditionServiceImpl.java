package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.MedicalConditionDto;
import com.example.utapCattle.model.entity.MedicalCondition;
import com.example.utapCattle.service.MedicalConditionService;
import com.example.utapCattle.service.repository.MedicalConditionRepository;

@Service
public class MedicalConditionServiceImpl implements MedicalConditionService {

	@Autowired
	private MedicalConditionRepository medicalConditionRepository;

	@Override
	public List<MedicalConditionDto> getAllMedicalCondition() {
		return medicalConditionRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	private MedicalConditionDto mapToDto(MedicalCondition medicalCond) {
		return new MedicalConditionDto(medicalCond.getMedicalConditionId(), medicalCond.getConditionDesc());
	}

}
