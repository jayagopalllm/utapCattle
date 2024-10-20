package com.example.utapCattle.controller;

import java.security.SecureRandom;

import com.example.utapCattle.model.dto.TbTestHistoryDto;
import com.example.utapCattle.model.dto.TreatmentHistoryMetadataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = { "http://localhost:4200", "http://35.178.210.158" })
public class TbTestHistoryController extends BaseController {

	@Autowired
	private TbTestHistoryService tbTestHistoryService;

	@Autowired
	private TreatmentHistoryService treatmentHistoryService;

	@PostMapping("/save")
	public ResponseEntity<?> saveTbTestHistory(@RequestBody final TreatmentHistoryMetadataDto treatmentHistoryMetadataDto) {
		logger.info("Saving tb test information: {}", treatmentHistoryMetadataDto);

    	try {
			treatmentHistoryMetadataDto.builder().
					processId(new SecureRandom().nextLong()).build();
			final TbTestHistoryDto savedTbTestHistory = tbTestHistoryService.saveTbTestHistory(treatmentHistoryMetadataDto.getTbTestHistory());
			logger.info("Saved tb test information with ID: {}", savedTbTestHistory.getTbTestHistoryId());
			treatmentHistoryService.saveTreatmentHistory(treatmentHistoryMetadataDto);
			return ResponseEntity.ok(savedTbTestHistory);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to save tb test information", e);
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}