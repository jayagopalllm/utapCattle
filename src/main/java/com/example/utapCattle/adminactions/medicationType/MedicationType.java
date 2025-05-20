package com.example.utapCattle.adminactions.medicationType;

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
@Table(name = "medicationtype")
public class MedicationType {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "medicationtypeid")
	private Long medicationTypeId;

	@Column(name = "medicationtypedesc")
	private String medicationTypeDesc;

	@Column(name = "category")
	private String category;

	@Column(name = "userfarmid")
	private Long userFarmId;
}
