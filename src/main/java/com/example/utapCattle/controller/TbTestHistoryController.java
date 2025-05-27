package com.example.utapCattle.controller;

import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.TbTestHistoryService;
import com.example.utapCattle.service.TreatmentHistoryService;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Map;

@RestController
@RequestMapping("/tbtest")
//@CrossOrigin(origins = { "http://localhost:4200", "http://35.178.210.158" })
public class TbTestHistoryController extends BaseController {

	private final TbTestHistoryService tbTestHistoryService;

	private final TreatmentHistoryService treatmentHistoryService;

	public TbTestHistoryController(TbTestHistoryService tbTestHistoryService, TreatmentHistoryService treatmentHistoryService) {
		this.tbTestHistoryService = tbTestHistoryService;
		this.treatmentHistoryService = treatmentHistoryService;
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveTbTestHistory(@RequestBody final TreatmentHistoryMetadata treatmentHistoryMetadata,HttpServletRequest request) throws Exception {
		logger.info("Saving tb test information: {}", treatmentHistoryMetadata);

    	try {
			Long userId = Long.parseLong(request.getHeader("User-ID"));
            treatmentHistoryMetadata.setUserId(userId);
			treatmentHistoryMetadata.setProcessId(new SecureRandom().nextLong());
			treatmentHistoryMetadata.setCattleId(treatmentHistoryMetadata.getTbTestHistory().getCattleId());
			final TbTestHistory savedTbTestHistory = tbTestHistoryService.saveTBTestData(treatmentHistoryMetadata);
			return ResponseEntity.ok(savedTbTestHistory);
		} catch (final Exception e) {
			//Log and rethrow exception , exception handled in GlobalExceptionHandler
			logger.error("Exception occurred: Unable to save tb test information", e);
        	throw e;
		}
	}

	@GetMapping("/{earTagOrEId}")
	public ResponseEntity<Map<String, Object>> getCattleDetailsAndAverageConditionScore(
			@PathVariable("earTagOrEId") final String earTagOrEId) {
		if (StringUtils.isEmpty(earTagOrEId)) {
			logger.warn("Invalid earTagOrEId provided");
			return ResponseEntity.badRequest().build();
		}
		try {
			final Map<String, Object> outputMap = tbTestHistoryService
					.getCattleDetailsAndTBTest(earTagOrEId);
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

}
