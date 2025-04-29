package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.InductionService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/induction")
public class InductionController extends BaseController {

    private final InductionService inductionService;

    public InductionController(InductionService inductionService) {
        this.inductionService = inductionService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveInduction(@RequestBody final TreatmentHistoryMetadata treatmentHistoryMetadata, HttpServletRequest request) {
        logger.info("Saving induction information: {}", treatmentHistoryMetadata);
        try {
            Long userId = Long.parseLong(request.getHeader("User-ID"));
            treatmentHistoryMetadata.setUserId(userId);
            final Map<String, Object> savedTreatmentHistoryDto = inductionService.saveInduction(treatmentHistoryMetadata);
            logger.info("Saved induction information");
            return new ResponseEntity<>(savedTreatmentHistoryDto, HttpStatus.CREATED);
        } catch (final Exception e) {
            //Log and rethrow exception , exception handled in GlobalExceptionHandler
            logger.error("Exception occurred: Unable to save induction information", e);
            throw e;
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            //return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list/{date}")
    public ResponseEntity<List<CattleDto>> getInductedCattleListByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,  HttpServletRequest request) {
        logger.info("Fetching induction information for date: {}", date);
        try {
            Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
            final List<CattleDto> cattleList = inductionService.getInductionList(date, userFarmId);
            logger.info("Fetched induction information");
            return new ResponseEntity<>(cattleList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to fetch induction information", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
