package com.example.utapCattle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medication")
public class Medication {

	@Id
	@Column(name = "medicationid")
	private int medicationId;

	@Column(name = "medicationdesc")
	private String medicationDesc;

	@Column(name = "medicationsupplierid")
	private int medicationSupplierId;

	@Column(name = "withdrawalperiod")
	private int withdrawalPeriod;

	@Column(name = "medicationtypeid")
	private int medicationTypeId;

	@Column(name = "batchnumber")
	private String batchNumber;

}
