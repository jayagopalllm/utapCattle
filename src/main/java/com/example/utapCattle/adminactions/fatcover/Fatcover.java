package com.example.utapCattle.adminactions.fatcover;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fatcover")
public class Fatcover {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fatcoverid")
	private Long fatcoverId;

	@Column(name = "fatcoverdesc ")
	private String fatcoverDesc;
}
