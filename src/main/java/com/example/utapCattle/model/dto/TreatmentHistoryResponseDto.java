package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
}
