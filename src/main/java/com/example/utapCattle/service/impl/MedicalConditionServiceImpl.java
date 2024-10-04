package com.example.utapCattle.service.impl;

import com.example.utapCattle.mapper.MedicalConditionMapper;
import com.example.utapCattle.model.dto.MedicalConditionDto;
import com.example.utapCattle.service.MedicalConditionService;
import com.example.utapCattle.service.repository.MedicalConditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalConditionServiceImpl implements MedicalConditionService {

	private final MedicalConditionRepository medicalConditionRepository;

	private final MedicalConditionMapper mapper;

	public MedicalConditionServiceImpl(MedicalConditionRepository medicalConditionRepository, MedicalConditionMapper mapper) {
		this.medicalConditionRepository = medicalConditionRepository;
		this.mapper = mapper;
	}

	@Override
	public List<MedicalConditionDto> getAllMedicalCondition() {
		return medicalConditionRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

}
