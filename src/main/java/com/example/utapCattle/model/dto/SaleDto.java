package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto {
	private Long saleId;
	private String saleDate;
	private Long saleMarketId;
	private String saleMarketName;
	private Long cattleId;
	private Long penId;
	private String comment;
	private Double weight;
    public Boolean newTagReq;

}
