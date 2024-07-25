package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.Treatment;
import com.example.utapCattle.service.TreatmentService;

@RestController
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/treatment/{id}")
    public ResponseEntity<Treatment> getTreatmentById(@PathVariable Long id) {
        Treatment treatment = treatmentService.getTreatmentById(id);
        if (treatment != null) {
            return ResponseEntity.ok(treatment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/treatment") // Endpoint for fetching all owners
    public List<Treatment> getAllTreatments() {
        return treatmentService.getAllTreatments();
    }
    
}
