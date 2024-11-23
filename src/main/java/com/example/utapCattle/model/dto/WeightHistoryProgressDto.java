package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightHistoryProgressDto {
	private String date;
	private Double weight;
	private Double previousWeight;
	private Double weightDiff;
	private Double dlwg;

	@Override
	public String toString() {
		return "WeightHistoryInfo{" + "date='" + date + '\'' + ", weight=" + weight + ", previousWeight="
				+ previousWeight + ", weightDiff=" + weightDiff + ", dlwg=" + dlwg + '}';
	}

}
