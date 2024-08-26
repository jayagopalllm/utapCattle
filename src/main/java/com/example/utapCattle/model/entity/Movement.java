package com.example.utapCattle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movement", schema = "public")
public class Movement {

	@Id
	@Column(name = "movementid")
	private Long movementId;

	@Column(name = "cattleid")
	private Long cattleId;

	@Column(name = "penid")
	private Integer penId;

	@Column(name = "movementdate")
	private String movementDate;

	@Column(name = "userid")
	private Long userId;

	@Column(name = "comment")
	private String comment;
}
