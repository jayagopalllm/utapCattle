package com.example.utapCattle.model.entity;

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
@Table(name = "sale", schema = "public")
public class Sale {
	@Id
	@Column(name = "saleid")
	private Long saleId;

	@Column(name = "saledate")
	private String saleDate;

	@Column(name = "salemarketid")
	private Long saleMarketId;

}