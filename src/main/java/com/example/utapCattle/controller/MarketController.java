package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.service.MarketService;

@RestController
@RequestMapping("/market")
public class MarketController extends BaseController {

    @Autowired
    private MarketService marketService;

    @GetMapping("/{id}")
    public ResponseEntity<MarketDto> getMarketById(@PathVariable Long id) {
        logger.info("Incoming request: Retrieving market with ID: {}", id);
        MarketDto marketDto = marketService.getMarketById(id);
        if (marketDto != null) {
            logger.info("Request successful: Retrieved market with ID: {}", id);
            return ResponseEntity.ok(marketDto);
        } else {
            logger.warn("Request failed: Market not found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<MarketDto> getAllMarkets() {
        logger.info("Incoming request: Retrieving all markets");
        List<MarketDto> markets = marketService.getAllMarkets();
        logger.info("Request successful: Retrieved {} markets", markets.size());
        return markets;
    }

    @PostMapping("/save")
    public ResponseEntity<MarketDto> saveMarket(@RequestBody Market market) {
        logger.info("Incoming request: Saving new market: {}", market);
        MarketDto savedMarketDto = marketService.saveMarket(market);
        logger.info("Request successful: Saved market with ID: {}", savedMarketDto.getMarketId());
        return new ResponseEntity<>(savedMarketDto, HttpStatus.CREATED);
    }
}
