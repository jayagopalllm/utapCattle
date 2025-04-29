package com.example.utapCattle.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "sale")
public class Sale {
	@Id
	@Column(name = "saleid")
	private Long saleId;

	@Column(name = "saledate")
	private String saleDate;

	@Column(name = "salemarketid")
	private Long saleMarketId;

	@Column(name = "userid")
	private Long userId;

	@CreationTimestamp
	@Column(name = "createdon",updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@Column(name = "updatedon")
	private LocalDateTime updatedOn;

}
