package com.example.utapCattle.model.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryMetadata {

	private List<TreatmentHistory> treatmentHistories;

	private Long cattleId;

	private String earTag;

	private String comment;

	private Boolean recordWeight;

	private Boolean recordMovement;

}