package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightHistoryInfo {
	private Long eid;
	private Double lastWeight;
	private Double lastDLWG;
	private Double overallDLWG;
}