package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketDto {

    private Integer marketId;
    private String marketName;
    private String holdingNumber;
    private String current; 

}
