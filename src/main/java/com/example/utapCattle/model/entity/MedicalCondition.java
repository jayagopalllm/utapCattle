package com.example.utapCattle.model.entity;

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
@Table(name = "medicalcondition")
public class MedicalCondition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "medicalconditionid")
	private Long medicalConditionId;

	@Column(name = "conditiondesc")
	private String conditionDesc;

	@Column(name = "userfarmid")
	private Long userFarmId;

}
