package com.example.utapCattle.service.impl;

import com.example.utapCattle.adminactions.conformationgrade.ConformationGrade;
import com.example.utapCattle.model.dto.DefaultTreatmentDto;
import com.example.utapCattle.model.entity.DefaultTreatment;
import com.example.utapCattle.service.DefaultTreatmentService;
import com.example.utapCattle.service.repository.DefaultTreatmentRepository;
import com.example.utapCattle.service.repository.MedicalConditionRepository;
import com.example.utapCattle.service.repository.MedicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultTreatmentServiceImpl implements DefaultTreatmentService {

    private final DefaultTreatmentRepository defaultTreatmentRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private MedicalConditionRepository medicalConditionRepository;

    public DefaultTreatmentServiceImpl(DefaultTreatmentRepository defaultTreatmentRepository) {
        this.defaultTreatmentRepository = defaultTreatmentRepository;
    }

    @Override
    public List<DefaultTreatmentDto> getAllDefaultTreatment() {
        return defaultTreatmentRepository.findAll().stream()
                .map(this::populateTransientFields)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private DefaultTreatment populateTransientFields(DefaultTreatment defaultTreatment) {
        medicationRepository.findById(defaultTreatment.getMedicationId())
                .ifPresent(supplier -> defaultTreatment.setMedicationDesc(supplier.getMedicationDesc()));

        medicalConditionRepository.findById(defaultTreatment.getMedicalConditionId())
                .ifPresent(type -> defaultTreatment.setConditionDesc(type.getConditionDesc()));

        return defaultTreatment;
    }

    @Override
    public DefaultTreatmentDto getDefaultTreatmentById(final Long id) {
        final Optional<DefaultTreatment> defaultTreatment = defaultTreatmentRepository.findById(id);
        return defaultTreatment.map(this::mapToDto).orElse(null);
    }

    @Override
    public DefaultTreatmentDto saveDefaultTreatment(DefaultTreatment defaultTreatment) {
        Long nextId = defaultTreatmentRepository.getNextSequenceValue();
        defaultTreatment.setCompulsoryTreatmentId(nextId);
        DefaultTreatment savedDefaultTreatment = defaultTreatmentRepository.save(defaultTreatment);
        return mapToDto(savedDefaultTreatment);
    }

    private DefaultTreatmentDto mapToDto(DefaultTreatment defaultTreatment) {
        DefaultTreatmentDto dto = new DefaultTreatmentDto();

        dto.setCompulsoryTreatmentId(defaultTreatment.getCompulsoryTreatmentId());
        dto.setDescription(defaultTreatment.getDescription());
        dto.setMedicalConditionId(defaultTreatment.getMedicalConditionId());
        dto.setMedicationId(defaultTreatment.getMedicationId());
        dto.setConditionDesc(defaultTreatment.getConditionDesc());
        dto.setMedicationDesc(defaultTreatment.getMedicationDesc());

        return dto;

    }

    @Override
    public DefaultTreatmentDto update(Long id, DefaultTreatment condition) {

        return defaultTreatmentRepository.findById(id).map(existingCondition -> {
            existingCondition.setDescription(condition.getDescription());
            existingCondition.setMedicalConditionId(condition.getMedicalConditionId());
            existingCondition.setMedicationId(condition.getMedicationId());
            defaultTreatmentRepository.save(existingCondition);
            return mapToDto(populateTransientFields(existingCondition));
        }).orElseThrow(() -> new RuntimeException("Compolsory Treatment not found"));

    }

    @Override
    public void delete(Long id) {
        if (defaultTreatmentRepository.existsById(id)) {
            defaultTreatmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Compolsory Treatment not found");
        }
    }

}
