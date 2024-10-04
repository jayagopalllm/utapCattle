package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketDto {

    private Long marketId;
    private String marketName;
    private String holdingNumber;
    private String current; 

}
