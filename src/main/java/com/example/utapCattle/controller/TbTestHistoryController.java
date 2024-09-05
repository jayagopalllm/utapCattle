package com.example.utapCattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
		try {
			logger.info("Incoming request: Saving tb test information");
			// add process id
			treatmentHistoryMetadata.setProcessId(3L);
			treatmentHistoryMetadata.setCattleId(treatmentHistoryMetadata.getCattleId());

			final TbTestHistory tbTestHistory = treatmentHistoryMetadata.getTbTestHistory();

			final TbTestHistory savedTbTestHistory = tbTestHistoryService.saveTbTestHistory(tbTestHistory);

			treatmentHistoryService.saveTreatmentHistory(treatmentHistoryMetadata);

			return ResponseEntity.ok(savedTbTestHistory);
		} catch (final Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}