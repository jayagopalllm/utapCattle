package com.example.utapCattle.controller;

import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.TbTestHistoryService;
import com.example.utapCattle.service.TreatmentHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
@RequestMapping("/tbtest")
@CrossOrigin(origins = { "http://localhost:4200", "http://35.178.210.158" })
public class TbTestHistoryController extends BaseController {

	private final TbTestHistoryService tbTestHistoryService;

	private final TreatmentHistoryService treatmentHistoryService;

	public TbTestHistoryController(TbTestHistoryService tbTestHistoryService, TreatmentHistoryService treatmentHistoryService) {
		this.tbTestHistoryService = tbTestHistoryService;
		this.treatmentHistoryService = treatmentHistoryService;
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveTbTestHistory(@RequestBody final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		logger.info("Saving tb test information: {}", treatmentHistoryMetadata);

    	try {
			treatmentHistoryMetadata.setProcessId(new SecureRandom().nextLong());
			treatmentHistoryMetadata.setCattleId(treatmentHistoryMetadata.getCattleId());
			final TbTestHistory savedTbTestHistory = tbTestHistoryService.saveTbTestHistory(treatmentHistoryMetadata.getTbTestHistory());
			logger.info("Saved tb test information with ID: {}", savedTbTestHistory.getTbTestHistoryId());
			treatmentHistoryService.saveTreatmentHistory(treatmentHistoryMetadata);
			return ResponseEntity.ok(savedTbTestHistory);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to save tb test information", e);
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}