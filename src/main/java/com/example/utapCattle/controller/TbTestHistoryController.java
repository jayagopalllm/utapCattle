package com.example.utapCattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.service.TbTestHistoryService;

@RestController
@RequestMapping("/tbtest")
public class TbTestHistoryController {

	@Autowired
	private TbTestHistoryService tbTestHistoryService;

	@PostMapping("/save")
	public ResponseEntity<?> saveTbTestHistory(@RequestBody TbTestHistory tbTestHistory) {
		try {
			final TbTestHistory savedTbTestHistory = tbTestHistoryService.saveTbTestHistory(tbTestHistory);
			return ResponseEntity.ok(savedTbTestHistory);
		} catch (final Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}