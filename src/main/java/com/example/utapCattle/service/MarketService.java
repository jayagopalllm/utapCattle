package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.Market;

public interface MarketService {

    List<MarketDto> getAllMarkets();

    List<MarketDto> findAllMarketsByUserId(Long userId);

    MarketDto getMarketById(Long id);

    MarketDto saveMarket(Market market);

    MarketDto update(Long id, Market condition);

    void delete(Long id);
}
