package com.example.utapCattle.model.dto;

import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.model.entity.TreatmentHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryMetadataDto {

	private List<TreatmentHistoryDto> treatmentHistories;

	private TbTestHistoryDto tbTestHistory;

	private Long cattleId;

	private Long processId;

	private String earTag;

	private String comment;

	private Boolean recordWeight;

	private Double weight;

	private Boolean recordMovement;

	private Integer pen;

}