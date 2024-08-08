package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.Cattle;
import com.example.utapCattle.service.CattleService;

@RestController
@RequestMapping("/cattle")  // Base path for cattle endpoints
public class CattleController {

    @Autowired
    private CattleService cattleService;

    @GetMapping("/{id}")  // Get cattle by ID
    public ResponseEntity<Cattle> getCattleById(@PathVariable Long id) {
        Cattle cattle = cattleService.getCattleById(id);
        return (cattle != null) ? ResponseEntity.ok(cattle) : ResponseEntity.notFound().build();
    }

    @GetMapping  // Get all cattle
    public List<Cattle> getAllCattle() {
        return cattleService.getAllCattle();
    }
}
