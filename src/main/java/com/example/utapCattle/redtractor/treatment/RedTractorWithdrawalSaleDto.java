package com.example.utapCattle.redtractor.treatment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedTractorWithdrawalSaleDto {

  private String breed;
  private String earNumber;
  private String medication;
  private String treatmentDate;
  private String withdrawalPeriod;
  private String withdrawalDate;
  private String soldOn;
  private String holdingNumber;
  private String vendorName;
  private String holdingAddress;

}
