package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.service.CattleService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cattle")
@CrossOrigin
public class CattleController extends BaseController {

	private final CattleService cattleService;

	public CattleController(CattleService cattleService) {
		this.cattleService = cattleService;
	}

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
	public ResponseEntity<List<String>> findEarTagsWithIncompleteInduction(HttpServletRequest request) {
		try {
			Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
			final List<String> earTagList = cattleService.findEarTagsWithIncompleteInduction(userFarmId);
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
	public ResponseEntity<?> saveCattle(@RequestBody final Object input,HttpServletRequest request) {
		logger.info("Saving cattle: {}", input);

		try {
			// Check if input is a List
			Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
			if (input instanceof List) {
				List<Map<String, Object>> cattleList = (List<Map<String, Object>>) input;

				// Use ObjectMapper to convert Map to Cattle entity
				List<Long> savedIds = cattleList.stream()
						.map(cattleData -> {
							try {
								// Convert Map to Cattle object
								Cattle cattle = new ObjectMapper().convertValue(cattleData, Cattle.class);
								cattle.setUserFarmId(userFarmId);
								return cattleService.saveCattle(cattle).getId();
							} catch (Exception e) {
								logger.error("Failed to save cattle: {}", cattleData, e);
								throw new RuntimeException("Error saving cattle", e);
							}
						})
						.collect(Collectors.toList());

				logger.info("Saved cattle IDs: {}", savedIds);
				return ResponseEntity.status(HttpStatus.CREATED).body(savedIds);

			} else if (input instanceof Map) {
				// Single object save
				Map<String, Object> cattleData = (Map<String, Object>) input;
				Cattle cattle = new ObjectMapper().convertValue(cattleData, Cattle.class);
				cattle.setUserFarmId(userFarmId);
				CattleDto savedCattleDto = cattleService.saveCattle(cattle);
				logger.info("Saved cattle with ID: {}", savedCattleDto.getId());

				return ResponseEntity.status(HttpStatus.CREATED).body(savedCattleDto.getId());
			} else {
				// Invalid input type
				logger.error("Invalid input type. Expected List<Map<String, Object>> or Map<String, Object>.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input format.");
			}
		} catch (Exception e) {
			logger.error("Exception occurred: Unable to save cattle", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}


	@PutMapping("/update/{cattleId}")
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

	@PostMapping("/fetchCattleBySaleId")
	public ResponseEntity<List<CattleDto>> getAllCattleBySaleId(@RequestBody String saleId) {
		List<CattleDto> cattList = cattleService.getAllCattleBySaleId(Long.parseLong(saleId));
		return new ResponseEntity<>(cattList, HttpStatus.OK);
	}

}
