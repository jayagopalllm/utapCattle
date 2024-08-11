package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.Market;

public interface MarketService {

    List<MarketDto> getAllMarkets();

    MarketDto getMarketById(Long id);

    MarketDto saveMarket(Market market); 
}
