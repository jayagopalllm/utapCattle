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
@Table(name = "medicalcondition", schema = "public")
public class MedicalCondition {

	@Id
	@Column(name = "medicalconditionid")
	private String medicalConditionId;

	@Column(name = "conditiondesc")
	private String conditionDesc;

}
