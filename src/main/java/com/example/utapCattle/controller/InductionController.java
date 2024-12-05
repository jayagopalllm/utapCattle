package com.example.utapCattle.controller;

import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.InductionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
