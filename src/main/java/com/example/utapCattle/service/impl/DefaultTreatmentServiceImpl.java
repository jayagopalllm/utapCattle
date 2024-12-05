package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.DefaultTreatmentDto;
import com.example.utapCattle.model.entity.DefaultTreatment;
import com.example.utapCattle.service.DefaultTreatmentService;
import com.example.utapCattle.service.repository.DefaultTreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultTreatmentServiceImpl implements DefaultTreatmentService {

    private final DefaultTreatmentRepository defaultTreatmentRepository;

    public DefaultTreatmentServiceImpl( DefaultTreatmentRepository defaultTreatmentRepository) {
        this.defaultTreatmentRepository = defaultTreatmentRepository;
    }

    @Override
    public List<DefaultTreatmentDto> getAllDefaultTreatment() {
        return defaultTreatmentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
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
        return new DefaultTreatmentDto(
                defaultTreatment.getCompulsoryTreatmentId(),
                defaultTreatment.getDescription(),
                defaultTreatment.getMedicalConditionId(),
                defaultTreatment.getMedicationId()
        );
    }

}
