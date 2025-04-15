package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentHistoryResponseDto {
    private Long treatmentHistoryId;
    private Long cattleId;
    private String conditionDesc;
    private String medicationDesc;
    private String batchNumber;
    private String treatmentDate;
    private String withdrawalDate;
}
