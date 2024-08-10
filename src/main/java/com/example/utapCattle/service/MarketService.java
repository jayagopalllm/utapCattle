package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.Market;

public interface MarketService {

    List<Market> getAllMarkets();

    Market getMarketById(Long id);
}
