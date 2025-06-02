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

    private String conditionDesc;

    private String medicationDesc;
    private String batchNumber;

    public DefaultTreatmentDto(Long compulsoryTreatmentId, String description, Long medicalConditionId, Long medicationId, String batchNumber) {
        this.compulsoryTreatmentId = compulsoryTreatmentId;
        this.description = description;
        this.medicalConditionId = medicalConditionId;
        this.medicationId = medicationId;
        this.batchNumber = batchNumber;
    }

}
