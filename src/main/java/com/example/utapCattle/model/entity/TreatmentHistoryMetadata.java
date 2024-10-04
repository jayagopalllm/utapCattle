package com.example.utapCattle.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryMetadata {

	private List<TreatmentHistory> treatmentHistories;

	private TbTestHistory tbTestHistory;

	private Long cattleId;

	private Long processId;

	private String earTag;

	private String comment;

	private Boolean recordWeight;

	private Double weight;

	private Boolean recordMovement;

	private Integer pen;

}