package com.example.utapCattle.redtractor.movement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/red-tractor/movements")
public class RedTractorMovementController {

    private RedTractorMovementService service;

    public RedTractorMovementController(RedTractorMovementService service) {
        this.service = service;
    }

    @GetMapping("/buying")
    public ResponseEntity<List<RedTractorMovementDto>> getAllMovementList(HttpServletRequest request) {
        Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
        return ResponseEntity.ok(service.getAllMovementList(userFarmId));
    }

    @GetMapping("/deaths")
    public ResponseEntity<List<RedTractorMovementDto>> getDeathRecords(HttpServletRequest request) {
        Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
        return ResponseEntity.ok(service.getDeathRecords(userFarmId));
    }

}
