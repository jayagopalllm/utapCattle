package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.*;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.WeightHistoryService;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/weight")
@CrossOrigin(origins = { "http://localhost:4200", "http://35.178.210.158" })
public class WeightHistoryController extends BaseController {

	private final WeightHistoryService weightHistoryService;

	public WeightHistoryController(WeightHistoryService weightHistoryService) {
		this.weightHistoryService = weightHistoryService;
	}

	@GetMapping("/{cattleId}")
	public ResponseEntity<List<WeightHistoryProgressDto>> getWeightProgressData(@PathVariable final String cattleId) {
		if (StringUtils.isEmpty(cattleId)) {
			logger.warn("Invalid cattleId provided");
			return ResponseEntity.badRequest().build();
		}
		try {
			final List<WeightHistoryProgressDto> weightProgress = weightHistoryService
					.deriveWeightHistoryInfoByCattleId(cattleId);
			if (CollectionUtils.isEmpty(weightProgress)) {
				logger.warn("No weight data found for cattleId: {}", cattleId);
				return ResponseEntity.noContent().build();
			}
			logger.info("Retrieved all weight progress of {}", cattleId);
			return ResponseEntity.ok(weightProgress);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve weight data for cattleId: {}", cattleId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveWeightAndMovement(
			@RequestBody final TreatmentHistoryMetadata treatmentHistoryMetadata, HttpServletRequest request) {
		logger.info("Saving weight and movement data: {}", treatmentHistoryMetadata);
		try {
			Long userId = Long.parseLong(request.getHeader("User-ID"));
			treatmentHistoryMetadata.setUserId(userId);
			weightHistoryService.saveWeightAndMovement(treatmentHistoryMetadata);
			logger.info("Saved weight and movement data");
			return new ResponseEntity<>(treatmentHistoryMetadata, HttpStatus.CREATED);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to save weight and movement data", e);
			throw e;
		}
	}

	@GetMapping("/cattle-{penId}")
	public ResponseEntity<List<WeightHistoryInfo>> getCattleInfoByPenId(@PathVariable Long penId) {
		try {
			final List<WeightHistoryInfo> cattleWeightInfo = weightHistoryService.getWeightHistoryByPen(penId);
			if (CollectionUtils.isEmpty(cattleWeightInfo)) {
				logger.warn("No cattle weight info found for penId: {}", penId);
				return ResponseEntity.noContent().build();
			}
			logger.info("Retrieved cattle weight info for penId: {}", penId);
			return ResponseEntity.ok(cattleWeightInfo);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve cattle weight info for penId: {}", penId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/history/{date}")
	public ResponseEntity<?> getWeightHistoryByDate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,HttpServletRequest request) {
		Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
		List<WeightHistDto> result = weightHistoryService.getWeightHistoryForToday(date,userFarmId);
		return ResponseEntity.ok(result);
	}
}
