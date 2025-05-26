package com.example.utapCattle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long penId;

	@Column(name = "penname")
	private String penName;

	@Column(name = "pengroupid")
	private Long penGroupId;

	@Column(name = "userfarmid")
	private Long userFarmId;

	@Transient
	private String groupDesc;

}
