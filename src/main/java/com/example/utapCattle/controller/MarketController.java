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
        MarketDto marketDto = marketService.getMarketById(id);
        try {
            if (marketDto != null) {
                logger.info("Retrieved market with ID: {}", id);
                return ResponseEntity.ok(marketDto);
            } else {
                logger.warn("No Market found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        }catch (final Exception e) {
                logger.error("Exception occurred: Unable to retrieve market", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MarketDto>> getAllMarkets() {
        try {
            List<MarketDto> markets = marketService.getAllMarkets();
            logger.info("Retrieved {} markets", markets.size());
            return ResponseEntity.ok(markets);
        } catch (final Exception e) {
            logger.error("Exception occurred: Unable to retrieve markets", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<MarketDto> saveMarket(@RequestBody Market market) {
        logger.info("Saving new market: {}", market);
        try {
            MarketDto savedMarketDto = marketService.saveMarket(market);
            logger.info("Saved market with ID: {}", savedMarketDto.getMarketId());
            return new ResponseEntity<>(savedMarketDto, HttpStatus.CREATED);
        } catch (final Exception e) {
            logger.error("Exception occurred: Unable to save market", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
