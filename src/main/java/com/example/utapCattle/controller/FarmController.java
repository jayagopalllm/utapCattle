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
        try {
            FarmDto farmDto = farmService.getFarmById(id);
            if (farmDto != null) {
                logger.info("Retrieved farm with ID: {}", id);
                return ResponseEntity.ok(farmDto);
            } else {
                logger.warn("No Farm found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve farm with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<FarmDto>> getAllFarms() {
        try {
            List<FarmDto> farms = farmService.getAllFarms();
            logger.info("Retrieved {} farms", farms.size());
            return ResponseEntity.ok(farms);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve all farms", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveFarm(@RequestBody Farm farm) {
        logger.info("Saving new farm: {}", farm);
        try {
            FarmDto savedFarmDto = farmService.saveFarm(farm);
            logger.info("Saved farm with ID: {}", savedFarmDto.getFarmId());
            return new ResponseEntity<>(savedFarmDto.getFarmId(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to save farm", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
