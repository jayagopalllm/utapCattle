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
@RequestMapping("/cattle")
@CrossOrigin
public class CattleController extends BaseController {

	@Autowired
	private CattleService cattleService;

	@GetMapping("/{id}")
	public ResponseEntity<CattleDto> getCattleById(@PathVariable final Long id) {
		logger.info("Incoming request: Retrieving cattle with ID: {}", id);
		final CattleDto cattleDto = cattleService.getCattleById(id);
		if (cattleDto != null) {
			logger.info("Request successful: Retrieved cattle with ID: {}", id);
			return ResponseEntity.ok(cattleDto);
		} else {
			logger.warn("Request failed: Cattle not found for ID: {}", id);
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public List<CattleDto> getAllCattle() {
		logger.info("Incoming request: Retrieving all cattle");
		final List<CattleDto> cattleList = cattleService.getAllCattle();
		logger.info("Request successful: Retrieved {} cattle", cattleList.size());
		return cattleList;
	}

	@GetMapping("/eartag") // Get cattle by ID
	public ResponseEntity<List<String>> findEarTagsWithIncompleteInduction() {
		logger.info("Incoming request: Retrieving all cattle's eartags that has not completed the induction");
		final List<String> earTagList = cattleService.findEarTagsWithIncompleteInduction();
		logger.info("Request successful: Retrieved all cattle's eartag");
		return (CollectionUtils.isEmpty(earTagList)) ? ResponseEntity.noContent().build()
				: ResponseEntity.ok(earTagList);
	}

	@GetMapping("/eartag/{earTag}")
	public ResponseEntity<CattleDto> getCattleByEarTag(@PathVariable String earTag) {
		final CattleDto cattle = cattleService.getCattleByEarTag(earTag);
		if (cattle != null) {
			return ResponseEntity.ok(cattle);
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveCattle(@RequestBody final Cattle cattle) {
		logger.info("Incoming request: Saving new cattle: {}", cattle);
		try {
			final CattleDto savedCattleDto = cattleService.saveCattle(cattle);
			logger.info("Request successful: Saved cattle with ID: {}", savedCattleDto.getId());
			return new ResponseEntity<>(savedCattleDto, HttpStatus.CREATED);
		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
