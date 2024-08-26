package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementDto {
	private Long movementId;
	private Long cattleId;
	private Integer penId;
	private String movementDate;
	private Long userId;
	private String comment;
}
