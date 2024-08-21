package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationDto {

	private int medicationId;

	private String medicationDesc;

	private int medicationSupplierId;

	private int withdrawalPeriod;

	private int medicationTypeId;

	private String batchNumber;

}
