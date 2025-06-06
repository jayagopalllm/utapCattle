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

    private String medicalConditionDesc;

    private String medicationDesc;
    private String batchNumber;
    private Long userFarmId;


    public DefaultTreatmentDto(Long compulsoryTreatmentId, String description, String conditionDesc, String medicationDesc, String batchNumber, Long userFarmId) {
        this.compulsoryTreatmentId = compulsoryTreatmentId;
        this.description = description;
        this.medicalConditionDesc = conditionDesc;
        this.medicationDesc = medicationDesc;
        this.batchNumber = batchNumber;
    }

}
