package com.example.utapCattle.controller;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.TbTestHistoryService;
import com.example.utapCattle.service.TreatmentHistoryService;

@RestController
@RequestMapping("/tbtest")
public class TbTestHistoryController extends BaseController {

	@Autowired
	private TbTestHistoryService tbTestHistoryService;

	@Autowired
	private TreatmentHistoryService treatmentHistoryService;

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