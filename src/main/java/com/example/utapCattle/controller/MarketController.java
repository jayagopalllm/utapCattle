package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.Market;
import com.example.utapCattle.service.MarketService;

@RestController
@RequestMapping("/market") // Base path for market endpoints
public class MarketController {

    @Autowired
    private MarketService marketService;

    @GetMapping("/{id}") // Get market by ID
    public ResponseEntity<Market> getMarketById(@PathVariable Long id) {
        Market market = marketService.getMarketById(id);
        return (market != null) ? ResponseEntity.ok(market) : ResponseEntity.notFound().build();
    }

    @GetMapping // Get all markets
    public List<Market> getAllMarkets() {
        return marketService.getAllMarkets();
    }
}
