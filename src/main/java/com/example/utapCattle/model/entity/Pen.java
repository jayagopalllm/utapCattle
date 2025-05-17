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
@Table(name = "pen")
public class Pen {

	@Id
	@Column(name = "penid")
	private Long penId;

	@Column(name = "penname")
	private String penName;

	@Column(name = "pengroupid")
	private Integer penGrounpId;

}
