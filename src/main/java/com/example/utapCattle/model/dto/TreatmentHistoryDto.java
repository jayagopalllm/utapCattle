package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryDto {

	private Long treatmentHistoryId;
	private Long cattleId;
	private Long userId;
	private Long medicalConditionId;
	private Long medicationId;
	private String batchNumber;
	private Double quantity;
	private String treatmentDate;
	private Long commentId;
	private Long conditionCommentId;
}
