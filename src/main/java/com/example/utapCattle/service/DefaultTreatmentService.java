package com.example.utapCattle.service;

import com.example.utapCattle.model.dto.DefaultTreatmentDto;
import com.example.utapCattle.model.entity.DefaultTreatment;

import java.util.List;

public interface DefaultTreatmentService {

    DefaultTreatmentDto getDefaultTreatmentById(final Long id);

    DefaultTreatmentDto saveDefaultTreatment(final DefaultTreatment defaultTreatment) throws Exception;

    List<DefaultTreatmentDto> getAllDefaultTreatment();
}
