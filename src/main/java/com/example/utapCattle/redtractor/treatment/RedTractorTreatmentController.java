package com.example.utapCattle.redtractor.treatment;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/red-tractor/treatments")
public class RedTractorTreatmentController {

    private final RedTractorTreatmentService treatmentService;

    public RedTractorTreatmentController(RedTractorTreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping("/withdrawal-period")
    public ResponseEntity<List<RedTractorWithdrawalSaleDto>> getWithdrawalPeriodSale(HttpServletRequest request) {
        Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
        return ResponseEntity.ok(treatmentService.getWithdrawalPeriodSale(userFarmId));
    }

    @GetMapping
    public ResponseEntity<List<RedTractorTreatmentDto>> getAllTreatmentList(HttpServletRequest request) {
        Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
        return ResponseEntity.ok(treatmentService.getAllTreatmentList(userFarmId));
    }

}
