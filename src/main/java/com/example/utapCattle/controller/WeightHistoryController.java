package com.example.utapCattle.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.WeightHistoryInfo;
import com.example.utapCattle.model.dto.WeightHistoryProgressDto;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.WeightHistoryService;

@RestController
@RequestMapping("/weight")
@CrossOrigin(origins = { "http://localhost:4200", "http://35.178.210.158" })
public class WeightHistoryController extends BaseController {

	@Autowired
	private WeightHistoryService weightHistoryService;

	@GetMapping("/{cattleId}")
	public List<WeightHistoryProgressDto> getWeightProgressData(@PathVariable final String cattleId) {
		if (StringUtils.isEmpty(cattleId)) {
			throw new IllegalArgumentException("Invalid cattleId");
		}
		logger.info("Incoming request: Retrieving all weight data for {}", cattleId);
		final List<WeightHistoryProgressDto> weightProgress = weightHistoryService
				.deriveWeightHistoryInfoByCattleId(Long.parseLong(cattleId));
		logger.info("Request successful: Retrieved all weight progress of {}", cattleId);
		return weightProgress;
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveWeightAndMovement(
			@RequestBody final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		try {
			weightHistoryService.saveWeightAndMovement(treatmentHistoryMetadata);
			return new ResponseEntity<>(treatmentHistoryMetadata, HttpStatus.CREATED);
		} catch (final Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/cattles-{penId}")
	public ResponseEntity<List<WeightHistoryInfo>> getCattleInfoByPenId(@PathVariable Long penId) {
		final List<WeightHistoryInfo> cattleWeightInfo = weightHistoryService.getWeightHistoryByPen(penId);
		return CollectionUtils.isEmpty(cattleWeightInfo) ? ResponseEntity.noContent().build()
				: ResponseEntity.ok(cattleWeightInfo);
	}
}
