package com.example.utapCattle.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InductionDto {

	private Long id;
	private String barcode;
	private String eid;
	private String earTag;
	private String chipNumber;
	private String condition;

}