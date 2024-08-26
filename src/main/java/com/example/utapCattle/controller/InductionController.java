package com.example.utapCattle.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.InductionService;

@RestController
@CrossOrigin
@RequestMapping("/induction")
public class InductionController extends BaseController {

	@Autowired
	private InductionService inductionService;

	@PostMapping("/save")
	public ResponseEntity<?> saveInduction(@RequestBody final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		logger.info("Incoming request: Saving induction information");
		final Map<String, Object> savedTreatmentHistoryDto = inductionService.saveInduction(treatmentHistoryMetadata);
		logger.info("Request successful: saved induction");
		return new ResponseEntity<>(savedTreatmentHistoryDto, HttpStatus.CREATED);
	}

}
