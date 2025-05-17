package com.example.utapCattle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "medicationid")
	private Long medicationId;

	@Column(name = "medicationdesc")
	private String medicationDesc;

	@Column(name = "medicationsupplierid")
	private Long medicationSupplierId;

	@Column(name = "withdrawalperiod")
	private int withdrawalPeriod;

	@Column(name = "medicationtypeid")
	private Long medicationTypeId;

	@Column(name = "batchnumber")
	private String batchNumber;

	@Transient
    private String supplierName;

    @Transient
    private String medicationTypeDesc;

}
