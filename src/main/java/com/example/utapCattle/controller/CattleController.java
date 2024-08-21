package com.example.utapCattle.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.service.CattleService;

@RestController
@CrossOrigin
@RequestMapping("/cattle") // Base path for cattle endpoints
public class CattleController {

	@Autowired
	private CattleService cattleService;

	@GetMapping("/{id}") // Get cattle by ID
	public ResponseEntity<CattleDto> getCattleById(@PathVariable Long id) {
		final CattleDto cattleDto = cattleService.getCattleById(id);
		return (cattleDto != null) ? ResponseEntity.ok(cattleDto) : ResponseEntity.noContent().build();
	}

	@GetMapping // Get all cattle
	public List<CattleDto> getAllCattle() {
		return cattleService.getAllCattle();
	}

	@GetMapping("/eartag") // Get cattle by ID
	public ResponseEntity<List<String>> getEartags() {
		final List<String> earTagList = cattleService.getEartags();
		return (CollectionUtils.isEmpty(earTagList)) ? ResponseEntity.noContent().build()
				: ResponseEntity.ok(earTagList);
	}

	@PostMapping("/save") // Save a new cattle
	public ResponseEntity<?> saveCattle(@RequestBody Cattle cattle) {
		try {
			final CattleDto savedCattleDto = cattleService.saveCattle(cattle);
			return new ResponseEntity<>(savedCattleDto, HttpStatus.CREATED);
		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
