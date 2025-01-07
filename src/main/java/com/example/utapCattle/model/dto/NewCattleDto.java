package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCattleDto {
	private Long id;
	private Long cattleId;
	private String prefix;
	private String earTag;
	private String dateOfBirth;
	private String motherEarTag;
	private Integer breedId;
	private Integer categoryId;
	private Boolean isInductionCompleted;
}
