package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.service.SlaughterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/slaughter")
public class SlaughterController extends BaseController {

    private final SlaughterService slaughterService;

    public SlaughterController(SlaughterService slaughterService) {
        this.slaughterService = slaughterService;
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveAgent(
            @RequestParam("slaughterHouse") Long slaughterHouse,
            @RequestParam("file") MultipartFile file) {
        logger.info("Saving new slaughter: {}",slaughterHouse );
        try {
            Long value = slaughterService.saveSlaughterData(slaughterHouse , file);
            logger.info("Saved Slaughter data with ID: {}", value);
            return new ResponseEntity<>(value, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to save Agent", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
