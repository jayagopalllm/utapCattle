package com.example.utapCattle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "treatmenthistory")
public class TreatmentHistory {
	@Id
	@Column(name = "treatmenthistoryid")
	private Long treatmentHistoryId;

	@Column(name = "cattleid")
	private Long cattleId;

	@Column(name = "userid")
	private Long userId;

	@Column(name = "treatmentdate")
	private String treatmentDate;

	@Column(name = "medicalconditionid")
	private String medicalConditionId;

	@Column(name = "medicationid")
	private Long medicationId;

	@Column(name = "batchnumber")
	private String batchNumber;

	@Column(name = "conditionscore")
	private Integer conditionScore;

	@Column(name = "conditioncommentid")
	private Long conditionCommentId;

	@Column(name = "commentid")
	private Long commentId;

	@Column(name = "processid")
	private Long processId;

	@Column(name = "withdrawaldate")
	private String withdrawalDate;

	@Transient
	private String conditionComment;

	@Override
	public String toString() {
		return "TreatmentHistory{" + "treatmentHistoryId=" + treatmentHistoryId + ", cattleId=" + cattleId
				+ ", userId='" + userId + '\'' + ", medicalConditionId='" + medicalConditionId + '\''
				+ ", medicationId=" + medicationId + ", batchNumber='" + batchNumber + '\'' + ", treatmentDate='"
				+ treatmentDate + '\'' + ", commentId=" + commentId + ", conditionCommentId=" + conditionCommentId
				+ '\'' + '}';
	}

}
