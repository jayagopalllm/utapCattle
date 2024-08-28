package com.example.utapCattle.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
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

		logger.info("Incoming request: Saving treatment history information");
		// add process id
		treatmentHistoryMetadata.setProcessId(2L);
		final Map<String, Object> savedTreatmentHistoryDto = treatmentHistoryService
				.saveTreatmentHistory(treatmentHistoryMetadata);
		logger.info("Request successful: saved treatment history");
		return MapUtils.isNotEmpty(savedTreatmentHistoryDto)
				? new ResponseEntity<>(savedTreatmentHistoryDto, HttpStatus.CREATED)
				: ResponseEntity.badRequest().build();
	}

	@GetMapping("/{eId}")
	public ResponseEntity<List<TreatmentHistoryDto>> getTreatmentHistoryById(@PathVariable("eId") final Long eId) {
		logger.info("Incoming request: Get treatment history information by Id");
		final List<TreatmentHistoryDto> treatmentHistoryDto = treatmentHistoryService.getTreatmentHistoriesByEId(eId);
		logger.info("Request successful: Retreived treatment history information");
		return (treatmentHistoryDto != null) ? ResponseEntity.ok(treatmentHistoryDto)
				: ResponseEntity.noContent().build();
	}

	@GetMapping("/cattle-info/{earTagOrEId}")
	public ResponseEntity<Map<String, Object>> getCattleDetailsAndAverageConditionScore(
			@PathVariable("earTagOrEId") final String earTagOrEId) {
		if (StringUtils.isEmpty(earTagOrEId)) {
			throw new IllegalArgumentException("earTagOrEId cannot by empty");
		}
		logger.info("Incoming request: Get Cattle info and avg condition score by cattle Id Or Ear tag");
		try {
			final Map<String, Object> outputMap = treatmentHistoryService
					.getCattleDetailsAndAverageConditionScore(earTagOrEId);
			logger.info("Request successful: Retreived Cattle info and avg condition score");
			return (MapUtils.isNotEmpty(outputMap)) ? ResponseEntity.ok(outputMap) : ResponseEntity.noContent().build();
		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping
	public List<TreatmentHistoryDto> getAllTreatmentHistory() {
		return treatmentHistoryService.getAllTreatmentHistory();
	}

}
