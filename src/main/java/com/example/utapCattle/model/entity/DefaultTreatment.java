package com.example.utapCattle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compulsorytreatment", schema = "public")
public class DefaultTreatment {

	@Id
	@Column(name = "compulsorytreatmentid")
	private Long compulsoryTreatmentId;

	@Column(name = "description")
	private String description;

	@Column(name = "medicalconditionid")
	private Long medicalConditionId;

	@Column(name = "medicationid")
	private Long medicationId;
}
