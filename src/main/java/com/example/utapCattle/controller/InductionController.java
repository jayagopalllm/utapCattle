package com.example.utapCattle.controller;

import java.util.Map;

import com.example.utapCattle.model.dto.TreatmentHistoryMetadataDto;
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
	public ResponseEntity<Map<String, Object>> saveInduction(@RequestBody final TreatmentHistoryMetadataDto treatmentHistoryMetadataDto
	) {
		logger.info("Saving induction information: {}", treatmentHistoryMetadataDto);
		try {
			final Map<String, Object> savedTreatmentHistoryDto = inductionService.saveInduction(treatmentHistoryMetadataDto);
			logger.info("Saved induction information");
			return new ResponseEntity<>(savedTreatmentHistoryDto, HttpStatus.CREATED);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to save induction information", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
