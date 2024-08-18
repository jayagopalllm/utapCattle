package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.service.CattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cattle")
public class CattleController extends BaseController {

    @Autowired
    private CattleService cattleService;

    @GetMapping("/{id}")
    public ResponseEntity<CattleDto> getCattleById(@PathVariable Long id) {
        logger.info("Incoming request: Retrieving cattle with ID: {}", id);
        CattleDto cattleDto = cattleService.getCattleById(id);
        if (cattleDto != null) {
            logger.info("Request successful: Retrieved cattle with ID: {}", id);
            return ResponseEntity.ok(cattleDto);
        } else {
            logger.warn("Request failed: Cattle not found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<CattleDto> getAllCattle() {
        logger.info("Incoming request: Retrieving all cattle");
        List<CattleDto> cattleList = cattleService.getAllCattle();
        logger.info("Request successful: Retrieved {} cattle", cattleList.size());
        return cattleList;
    }

    @PostMapping("/save")
    public ResponseEntity<CattleDto> saveCattle(@RequestBody Cattle cattle) {
        logger.info("Incoming request: Saving new cattle: {}", cattle);
        CattleDto savedCattleDto = cattleService.saveCattle(cattle);
        logger.info("Request successful: Saved cattle with ID: {}", savedCattleDto.getCattleId()); // Assuming CattleDto has getCattleId()
        return new ResponseEntity<>(savedCattleDto, HttpStatus.CREATED);
    }
}
