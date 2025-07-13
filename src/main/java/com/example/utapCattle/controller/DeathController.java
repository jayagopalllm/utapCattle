package com.example.utapCattle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.entity.DeathRecord;
import com.example.utapCattle.service.DeathService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/death-registration")
public class DeathController {

    private final DeathService service;

    public DeathController(DeathService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getOnloadData(HttpServletRequest request) {
        Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
        return ResponseEntity.ok(service.getOnloadData(userFarmId));
    }

    @PostMapping()
    public List<String> create(@RequestBody DeathRecord deathRecord, HttpServletRequest request) {
        return service.save(deathRecord);
    }




}
