package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketDto {

    private Long marketId;
    private Integer sourceMarketId;
    private String marketName;
    private String holdingNumber;
    private String current;

}
