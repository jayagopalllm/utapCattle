package com.example.utapCattle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment", schema = "public")
public class Comment {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "comments")
	private String comments;

	@Column(name = "processid")
	private Long processId;

	@Column(name = "cattleid")
	private Long cattleId;

	@Column(name = "userid")
	private Long userId;

	@Column(name = "entityid")
	private Long entityId;

	@Column(name = "commentdate")
	private String commentDate;

}
