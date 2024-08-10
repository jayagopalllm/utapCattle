package com.example.utapCattle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.Market;
import com.example.utapCattle.service.MarketService;
import com.example.utapCattle.service.repository.MarketRepository;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    private MarketRepository marketRepository;

    @Override
    public List<Market> getAllMarkets() {
        return marketRepository.findAll();
    }

    @Override
    public Market getMarketById(Long id) {
        return marketRepository.findById(id).orElse(null);
    }
}
