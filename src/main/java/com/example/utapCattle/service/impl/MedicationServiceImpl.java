package com.example.utapCattle.service.impl;

import com.example.utapCattle.mapper.MedicationMapper;
import com.example.utapCattle.model.dto.MedicationDto;
import com.example.utapCattle.service.MedicationService;
import com.example.utapCattle.service.repository.MedicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicationServiceImpl implements MedicationService {

	private final MedicationRepository medicationRepository;

	private final MedicationMapper mapper;

	public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper mapper) {
		this.medicationRepository = medicationRepository;
		this.mapper = mapper;
	}

	@Override
	public List<MedicationDto> getAllMedication() {
		return medicationRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}
}
