package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.service.MarketService;
import com.example.utapCattle.service.MovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/movement")
public class MovementController extends BaseController {

    private final MarketService marketService;
    private final MovementService movementService;

    public MovementController(MarketService marketService,MovementService movementService) {
        this.marketService = marketService;
        this.movementService = movementService;
    }

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
    public ResponseEntity<Long> saveMovementFileData(@RequestParam("file") MultipartFile file) {
        logger.info("Saving new market: {}", file.getName());
        try {
            Long id = movementService.saveMovementFileData(file);
            logger.info("Saved market with ID: {}", id);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (final Exception e) {
            logger.error("Exception occurred: Unable to save market", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
