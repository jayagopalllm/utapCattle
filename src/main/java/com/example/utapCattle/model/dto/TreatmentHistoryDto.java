package com.example.utapCattle.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryDto {

	private Long treatmentHistoryId;
	private Long cattleId;
	private String userId;
	private String treatmentDate;
	private String medicalConditionId;
	private Long medicationId;
	private String batchNumber;
	private Integer conditionScore;
	private Long conditionCommentId;
	private Long commentId;
	private Long processId;
	private String conditionComment;
}
