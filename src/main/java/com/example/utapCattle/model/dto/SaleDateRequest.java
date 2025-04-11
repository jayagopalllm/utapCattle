package com.example.utapCattle.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDateRequest {

    private String newDate;

    private Long sellerMarketId;

}

