package com.example.utapCattle.controller;

import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.InductionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    public ResponseEntity<Map<String, Object>> saveInduction(@RequestBody final TreatmentHistoryMetadata treatmentHistoryMetadata) {
        logger.info("Saving induction information: {}", treatmentHistoryMetadata);
        try {
            final Map<String, Object> savedTreatmentHistoryDto = inductionService.saveInduction(treatmentHistoryMetadata);
            logger.info("Saved induction information");
            return new ResponseEntity<>(savedTreatmentHistoryDto, HttpStatus.CREATED);
        } catch (final Exception e) {
            logger.error("Exception occurred: Unable to save induction information", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/list/{date}")
    public ResponseEntity<List<Cattle>> getInductedCattleListByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        logger.info("Fetching induction information for date: {}", date);
        try {
            final List<Cattle> cattleList = inductionService.getInductionList(date);
            logger.info("Fetched induction information");
            return new ResponseEntity<>(cattleList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to fetch induction information", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
