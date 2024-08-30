package com.example.utapCattle.model.entity;

import java.time.LocalDateTime;

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
@Table(name = "tbtesthistory")
public class TbTestHistory {

	@Id
	@Column(name = "tbtesthistoryid")
	private Long tbTestHistoryId;

	@Column(name = "cattleid")
	private Long cattleId;

	@Column(name = "userid1")
	private Long userId1;

	@Column(name = "userid2")
	private Long userId2;

	@Column(name = "testdate")
	private LocalDateTime testDate;

	@Column(name = "measa1")
	private Integer measA1;

	@Column(name = "measb1")
	private Integer measB1;

	@Column(name = "measa2")
	private Integer measA2;

	@Column(name = "measb2")
	private Integer measB2;

	@Column(name = "reactiondesca")
	private String reactionDescA;

	@Column(name = "reactiondescb")
	private String reactionDescB;

	@Column(name = "overallresult")
	private String overallResult;

	@Column(name = "remarks")
	private String remarks;

	@Transient
	private String earTag;
}
