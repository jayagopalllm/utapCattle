package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.service.FarmService;

@RestController
@RequestMapping("/farm")  // Base path for farm endpoints
public class FarmController {

    @Autowired
    private FarmService farmService;

    @GetMapping("/{id}")  // Get farm by ID
    public ResponseEntity<FarmDto> getFarmById(@PathVariable Long id) {
        FarmDto farmDto = farmService.getFarmById(id);
        return (farmDto != null) ? ResponseEntity.ok(farmDto) : ResponseEntity.notFound().build();
    }

    @GetMapping  // Get all farms
    public List<FarmDto> getAllFarms() {
        return farmService.getAllFarms();
    }
}
