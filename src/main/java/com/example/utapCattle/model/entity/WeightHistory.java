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
@Table(name = "weighthistory")
public class WeightHistory {

	@Id
	@Column(name = "weighthistoryid")
	private Long weightHistoryId;

	@Column(name = "cattleid")
	private Long cattleId;

	@Column(name = "weightdatetime")
	private String weightDateTime;

	@Column(name = "weight")
	private Double weight;

	@Column(name = "userid")
	private Long userId;

	@Override
	public String toString() {
		return "WeightHistory{" + "weightHistoryId=" + weightHistoryId + ", cattleId=" + cattleId + ", weightDateTime='"
				+ weightDateTime + '\'' + ", weight=" + weight + ", userId='" + userId + '\'' + '}';
	}
}
