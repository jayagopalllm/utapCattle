package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.dto.TreatmentHistoryResponseDto;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.TreatmentHistoryService;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/treatment")
public class TreatmentHistoryController extends BaseController {

	private final TreatmentHistoryService treatmentHistoryService;

	public TreatmentHistoryController(TreatmentHistoryService treatmentHistoryService) {
		this.treatmentHistoryService = treatmentHistoryService;
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveTreatmentHistory(
			@RequestBody final TreatmentHistoryMetadata treatmentHistoryMetadata, HttpServletRequest request) {
		logger.info("Saving treatment history information: {}", treatmentHistoryMetadata);
		try {
			Long userId = Long.parseLong(request.getHeader("User-ID"));
			treatmentHistoryMetadata.setUserId(userId);
			treatmentHistoryMetadata.setProcessId(new SecureRandom().nextLong());
			final Map<String, Object> savedTreatmentHistoryDto = treatmentHistoryService
					.saveTreatmentHistory(treatmentHistoryMetadata);
			logger.info("saved treatment history");
			return new ResponseEntity<>(savedTreatmentHistoryDto, HttpStatus.CREATED);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to save treatment history information", e);
			throw e; // Rethrow exception to be handled by GlobalExceptionHandler
		}
	}

	@GetMapping("/{eId}")
	public ResponseEntity<List<TreatmentHistoryDto>> getTreatmentHistoryById(@PathVariable("eId") final Long eId) {
		try {
			final List<TreatmentHistoryDto> treatmentHistoryDto = treatmentHistoryService.getTreatmentHistoriesByEId(eId);
			if (treatmentHistoryDto == null) {
				logger.warn("No treatment history found for eId: {}", eId);
				return ResponseEntity.noContent().build();
			}
			logger.info("Retrieved treatment history information");
			return ResponseEntity.ok(treatmentHistoryDto);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve treatment history for eId: {}", eId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/cattle-info/{earTagOrEId}")
	public ResponseEntity<Map<String, Object>> getCattleDetailsAndAverageConditionScore(
			@PathVariable("earTagOrEId") final String earTagOrEId) {
		if (StringUtils.isEmpty(earTagOrEId)) {
			logger.warn("Invalid earTagOrEId provided");
			return ResponseEntity.badRequest().build();
		}
		try {
			final Map<String, Object> outputMap = treatmentHistoryService
					.getCattleDetailsAndAverageConditionScore(earTagOrEId);
			if (MapUtils.isEmpty(outputMap)) {
				logger.warn("No cattle info found for earTagOrEId: {}", earTagOrEId);
				return ResponseEntity.noContent().build();
			}
			logger.info("Retrieved Cattle info and avg condition score");
			return ResponseEntity.ok(outputMap);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve cattle info for earTagOrEId: {}", earTagOrEId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping
	public ResponseEntity<List<TreatmentHistoryDto>> getAllTreatmentHistory() {
		try {
			final List<TreatmentHistoryDto> treatmentHistoryDto = treatmentHistoryService.getAllTreatmentHistory();
			if (treatmentHistoryDto == null) {
				logger.warn("No treatment history found");
				return ResponseEntity.noContent().build();
			}
			logger.info("Retrieved all treatment history information");
			return ResponseEntity.ok(treatmentHistoryDto);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve treatment history", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/history/{cattleId}")
	public ResponseEntity<List<TreatmentHistoryResponseDto>> getTreatmentHistoryByCattleId(
			@PathVariable("cattleId") final Long cattleId) {
				System.out.println("Fetching treatment history for cattleId: " + cattleId);
		final List<TreatmentHistoryResponseDto> treatmentHistoryResponseDto = treatmentHistoryService
				.getTreatmentHistoriesByCattleId(cattleId);
		return ResponseEntity.ok(treatmentHistoryResponseDto);
	}

}
