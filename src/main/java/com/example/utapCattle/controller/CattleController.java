package com.example.utapCattle.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		try {
			final CattleDto cattleDto = cattleService.getCattleById(id);
			if (cattleDto != null) {
				logger.info("Retrieved cattle with ID: {}", id);
				return ResponseEntity.ok(cattleDto);
			} else {
				logger.warn("No Cattle found with ID: {}", id);
				return ResponseEntity.notFound().build();
			}
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve cattle with ID: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping
	public ResponseEntity<List<CattleDto>> getAllCattle() {
		try {
			final List<CattleDto> cattleList = cattleService.getAllCattle();
			logger.info("Retrieved {} cattle", cattleList.size());
			return ResponseEntity.ok(cattleList);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve all cattle", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/eartag")
	public ResponseEntity<List<String>> findEarTagsWithIncompleteInduction() {
		try {
			final List<String> earTagList = cattleService.findEarTagsWithIncompleteInduction();
			if (CollectionUtils.isEmpty(earTagList)) {
				logger.warn("No cattle ear tags found with incomplete induction");
				return ResponseEntity.noContent().build();
			} else {
				logger.info("Retrieved {} cattle ear tags with incomplete induction", earTagList.size());
				return ResponseEntity.ok(earTagList);
			}
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve cattle ear tags", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/eartag/{earTag}")
	public ResponseEntity<CattleDto> getCattleByEarTag(@PathVariable String earTag) {
		try {
			final CattleDto cattle = cattleService.getCattleByEarTag(earTag);
			if (cattle != null) {
				logger.info("Retrieved cattle with ear tag: {}", earTag);
				return ResponseEntity.ok(cattle);
			}
			logger.warn("No Cattle found with ear tag: {}", earTag);
			return ResponseEntity.noContent().build();
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve cattle with ear tag: {}", earTag, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/save")
	public ResponseEntity<Long> saveCattle(@RequestBody final Cattle cattle) {
		logger.info("Saving new cattle: {}", cattle);
		try {
			final CattleDto savedCattleDto = cattleService.saveCattle(cattle);
			logger.info("Saved cattle with ID: {}", savedCattleDto.getId());
			return new ResponseEntity<>(savedCattleDto.getId(), HttpStatus.CREATED);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to save cattle", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/update/{cattleid}")
	public ResponseEntity<Long> saveCattle(@PathVariable Long cattleId ,@RequestBody final Cattle cattle) {
		logger.info("Saving new cattle: {}", cattle);
		try {
			final CattleDto savedCattleDto = cattleService.updateCattle(cattleId,cattle);
			logger.info("Saved cattle with ID: {}", savedCattleDto.getId());
			return new ResponseEntity<>(savedCattleDto.getId(), HttpStatus.CREATED);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to save cattle", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
