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

	private TbTestHistory tbTestHistory;

	private Long cattleId;

	private Long processId;

	private String earTag;

	private String comment;

	private Boolean recordWeight;

	private Double weight;

	private Boolean recordMovement;

	private Integer pen;

	private Long userId;

	private Boolean newTagReq;

}
