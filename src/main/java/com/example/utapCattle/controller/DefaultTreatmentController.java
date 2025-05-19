package com.example.utapCattle.controller;

import com.example.utapCattle.adminactions.conformationgrade.ConformationGrade;
import com.example.utapCattle.model.dto.DefaultTreatmentDto;
import com.example.utapCattle.model.entity.DefaultTreatment;
import com.example.utapCattle.service.DefaultTreatmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/defaulttreatment")
public class DefaultTreatmentController extends BaseController {

    private final DefaultTreatmentService defaultTreatmentService;

    public DefaultTreatmentController(DefaultTreatmentService defaultTreatmentService) {
        this.defaultTreatmentService = defaultTreatmentService;
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<DefaultTreatmentDto> update(@PathVariable Long id, @RequestBody DefaultTreatment condition) {
        try {
            return ResponseEntity.ok(defaultTreatmentService.update(id, condition));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            defaultTreatmentService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
