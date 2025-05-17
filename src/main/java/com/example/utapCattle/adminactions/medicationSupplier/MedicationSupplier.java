package com.example.utapCattle.adminactions.medicationSupplier;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicationsupplier")
public class MedicationSupplier {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "medicationsupplierid")
	private Long medicationSupplierId;

	@Column(name = "suppliername")
	private String supplierName;
}
