package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.ScaleWeightDto;
import com.example.utapCattle.service.ScaleWeightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scale")
public class WeighScaleController extends BaseController {

    private final ScaleWeightService weightService;

    public WeighScaleController(ScaleWeightService weightService) {
        this.weightService = weightService;
    }

    @PostMapping("/weights")
    public ResponseEntity<ScaleWeightDto> saveWeight(@RequestBody ScaleWeightDto weightDto) {
        logger.info("Saving weight: {}", weightDto.getWeight());
        try {
            ScaleWeightDto savedWeightDto = weightService.saveWeight(weightDto);
            logger.info("Saved weight");
            return new ResponseEntity<>(savedWeightDto, HttpStatus.CREATED);
        } catch (final Exception e) {
            logger.error("Exception occurred: Unable to save weight", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
