package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.MarketDto;

public interface MarketService {

    List<MarketDto> getAllMarkets();

    MarketDto getMarketById(Long id);
}
