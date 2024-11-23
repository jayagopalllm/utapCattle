package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.MedicationDto;
import com.example.utapCattle.model.entity.Medication;
import com.example.utapCattle.service.MedicationService;
import com.example.utapCattle.service.repository.MedicationRepository;

@Service
public class MedicationServiceImpl implements MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;

	@Override
	public List<MedicationDto> getAllMedication() {
		return medicationRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	private MedicationDto mapToDto(Medication medication) {
		return new MedicationDto(medication.getMedicationId(), medication.getMedicationDesc(),
				medication.getMedicationSupplierId(), medication.getWithdrawalPeriod(),
				medication.getMedicationTypeId(), medication.getBatchNumber());
	}

}
