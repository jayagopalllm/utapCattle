package com.example.utapCattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.ScaleWeightDto;
import com.example.utapCattle.service.ScaleWeightService;

@RestController
@RequestMapping("/scale")
public class ScaleWeightController extends BaseController {

    @Autowired
    private ScaleWeightService weightService;

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
