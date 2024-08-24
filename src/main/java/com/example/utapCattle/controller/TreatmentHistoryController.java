package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.TreatmentHistoryService;

@RestController
@CrossOrigin
@RequestMapping("/treatment")
public class TreatmentHistoryController extends BaseController {

	@Autowired
	private TreatmentHistoryService treatmentHistoryService;

	@PostMapping("/save")
	public ResponseEntity<?> saveTreatmentHistory(
			@RequestBody final TreatmentHistoryMetadata treatmentHistoryMetadata) {

		logger.info("Incoming request: Saving induction information");
		final List<TreatmentHistoryDto> savedCattleDto = treatmentHistoryService
				.saveTreatmentHistory(treatmentHistoryMetadata);
		logger.info("Request successful: saved induction");
		return new ResponseEntity<>(savedCattleDto, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TreatmentHistoryDto> getTreatmentHistoryById(@PathVariable final Long id) {
		logger.info("Incoming request: Get Induction information by Id");
		final TreatmentHistoryDto inductionDto = treatmentHistoryService.getTreatmentHistoryById(id);
		logger.info("Request successful: Retreived induction information");
		return (inductionDto != null) ? ResponseEntity.ok(inductionDto) : ResponseEntity.noContent().build();
	}

	@GetMapping
	public List<TreatmentHistoryDto> getAllTreatmentHistory() {
		return treatmentHistoryService.getAllTreatmentHistory();
	}

}
