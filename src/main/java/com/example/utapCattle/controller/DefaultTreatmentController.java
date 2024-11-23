package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.DefaultTreatmentDto;
import com.example.utapCattle.model.entity.DefaultTreatment;
import com.example.utapCattle.service.DefaultTreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/defaulttreatment")
public class DefaultTreatmentController extends BaseController {

    @Autowired
    private DefaultTreatmentService defaultTreatmentService;

    @GetMapping("/{id}")
    public ResponseEntity<DefaultTreatmentDto> getDefaultTreatment(@PathVariable final Long id) {
        try {
            final DefaultTreatmentDto defaultTreatmentDto = defaultTreatmentService.getDefaultTreatmentById(id);
            if (defaultTreatmentDto != null) {
                logger.info("Retrieved default treatment with ID: {}", id);
                return ResponseEntity.ok(defaultTreatmentDto);
            } else {
                logger.warn("No default treatment found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (final Exception e) {
            logger.error("Exception occurred: Unable to retrieve default treatment with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveDefaultTreatment(@RequestBody final DefaultTreatment defaultTreatment) {
        logger.info("Saving new retrieve default treatment: {}", defaultTreatment);
        try {
            final DefaultTreatmentDto defaultTreatmentDto = defaultTreatmentService.saveDefaultTreatment(
                    defaultTreatment);
            logger.info("Saved retrieve default treatment with ID: {}",
                    defaultTreatmentDto.getCompulsoryTreatmentId());
            return new ResponseEntity(defaultTreatmentDto.getCompulsoryTreatmentId(), HttpStatus.CREATED);
        } catch (final Exception e) {
            logger.error("Exception occurred: Unable to save retrieve default treatment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DefaultTreatmentDto>> getAllCategories() {
        try {
            List<DefaultTreatmentDto> defaultTreatmentDto = defaultTreatmentService.getAllDefaultTreatment();
            logger.info("Retrieved {} categories", defaultTreatmentDto.size());
            return ResponseEntity.ok(defaultTreatmentDto);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve all default treatment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
