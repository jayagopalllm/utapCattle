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
@RequestMapping("/cattle")  // Base path for cattle endpoints
public class CattleController {

    @Autowired
    private CattleService cattleService;

    @GetMapping("/{id}")  // Get cattle by ID
    public ResponseEntity<CattleDto> getCattleById(@PathVariable Long id) {
        CattleDto cattleDto = cattleService.getCattleById(id);
        return (cattleDto != null) ? ResponseEntity.ok(cattleDto) : ResponseEntity.notFound().build();
    }

    @GetMapping  // Get all cattle
    public List<CattleDto> getAllCattle() {
        return cattleService.getAllCattle();
    }

    @PostMapping("/save") // Save a new cattle
    public ResponseEntity<CattleDto> saveCattle(@RequestBody Cattle cattle) {
        CattleDto savedCattleDto = cattleService.saveCattle(cattle);
        return new ResponseEntity<>(savedCattleDto, HttpStatus.CREATED);
    }
}
