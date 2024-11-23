package com.example.utapCattle.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTreatmentDto {

    private Long compulsoryTreatmentId;

    private String description;

    private Long medicalConditionId;

    private Long medicationId;
}
