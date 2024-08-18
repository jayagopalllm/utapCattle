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

import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.service.FarmService;

@RestController
@RequestMapping("/farm")
public class FarmController extends BaseController {

    @Autowired
    private FarmService farmService;

    @GetMapping("/{id}")
    public ResponseEntity<FarmDto> getFarmById(@PathVariable Long id) {
        logger.info("Incoming request: Retrieving farm with ID: {}", id);
        FarmDto farmDto = farmService.getFarmById(id);
        if (farmDto != null) {
            logger.info("Request successful: Retrieved farm with ID: {}", id);
            return ResponseEntity.ok(farmDto);
        } else {
            logger.warn("Request failed: Farm not found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping  // Get all farms
    public List<FarmDto> getAllFarms() {
        logger.info("Incoming request: Retrieving all farms");
        List<FarmDto> farms = farmService.getAllFarms();
        logger.info("Request successful: Retrieved {} farms", farms.size());
        return farms;
    }

    @PostMapping("/save") // Save a new farm
    public ResponseEntity<FarmDto> saveFarm(@RequestBody Farm farm) {
        logger.info("Incoming request: Saving new farm: {}", farm);
        FarmDto savedFarmDto = farmService.saveFarm(farm);
        logger.info("Request successful: Saved farm with ID: {}", savedFarmDto.getFarmId());
        return new ResponseEntity<>(savedFarmDto, HttpStatus.CREATED);
    }
}
