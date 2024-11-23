package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightHistoryDto {

	private Long weightHistoryId;
	private Long cattleId;
	private String weightDateTime;
	private Double weight;
	private Long userId;

	@Override
	public String toString() {
		return "WeightHistoryDto{" + "weightHistoryId=" + weightHistoryId + ", cattleId=" + cattleId
				+ ", weightDateTime='" + weightDateTime + '\'' + ", weight=" + weight + ", userId='" + userId + '\''
				+ '}';
	}
}
