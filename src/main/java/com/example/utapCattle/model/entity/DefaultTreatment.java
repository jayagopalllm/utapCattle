package com.example.utapCattle.model.entity;

import jakarta.persistence.*;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "compulsorytreatmentid", nullable = false)
	private Long compulsoryTreatmentId;

	@Column(name = "description")
	private String description;

	@Column(name = "medicalconditionid")
	private Long medicalConditionId;

	@Column(name = "medicationid")
	private Long medicationId;

	@Override
	public String toString() {
		return "DefaultTreatment{" +
				"compulsoryTreatmentId=" + compulsoryTreatmentId +
				", description='" + description + '\'' +
				", medicalConditionId='" + medicalConditionId + '\'' +
				", medicationId='" + medicationId + '\'' +
				'}';
	}
}
