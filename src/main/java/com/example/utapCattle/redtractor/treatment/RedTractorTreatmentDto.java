package com.example.utapCattle.redtractor.treatment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedTractorTreatmentDto {

  private String treatmentDate;
  private String earNumber;
  private String medication;
  private String batchNumber;
  private Double quantity;
  private String withdrawalPeriod;
  private String withdrawalDate;
  private String administeredBy;
  private String medicalCondition;

}
