package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationDto {

	private Long medicationId;

	private String medicationDesc;

	private Long medicationSupplierId;

	private int withdrawalPeriod;

	private Long medicationTypeId;

	private String batchNumber;

}
