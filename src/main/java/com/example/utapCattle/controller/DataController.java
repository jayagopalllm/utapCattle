package com.example.utapCattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.AllDataDto;
import com.example.utapCattle.service.DataService;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = { "http://localhost:4200", "http://35.178.210.158" })
public class DataController extends BaseController {

	@Autowired
	private DataService dataService;

	/**
	 * Endpoint to retrieve all necessary data for the CattleManagement application on startup.
	 * 
	 * @return An {@link AllDataDto} object containing all the required data
	 */
	@GetMapping()
	public ResponseEntity<AllDataDto> getAllData() {
		try {
			final AllDataDto allData = dataService.getAllData();
			logger.info("Retrieved all data");
			return ResponseEntity.ok(allData);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve all data", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Endpoint to retrieve data required for the medical condition section of the induction page in the CattleManagement application.
	 * 
	 * @return An {@link AllDataDto} object containing the necessary medical condition data.
	 */
	@GetMapping(value = "/condition")
	public ResponseEntity<AllDataDto> getMedicalConditionData() {
		try {
			final AllDataDto allData = dataService.getMedicalConditionData();
			logger.info("Retrieved medical condition data");
			return ResponseEntity.ok(allData);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve medical condition data", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Endpoint to retrieve data required for the treatment page in the CattleManagement application.
	 * 
	 * @return An {@link AllDataDto} object containing the necessary treatment data.
	 */
	@GetMapping(value = "/treatment")
	public ResponseEntity<AllDataDto> getTreatmentData() {
		try {
			final AllDataDto allData = dataService.getTreatmentData();
			logger.info("Retrieved treatment data");
			return ResponseEntity.ok(allData);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve treatment data", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Endpoint to retrieve data required for both the Weight&Sort page and the TBTest page in the CattleManagement application.
	 * 
	 * @return An {@link AllDataDto} object containing the necessary data for Weight&Sort and TBTest pages.
	 */
	@GetMapping(value = "/weight-tb")
	public ResponseEntity<AllDataDto> getTBTestData() {
		try {
			final AllDataDto allData = dataService.getWeightAndTBTestData();
			logger.info("Retrieved Weigtht and TB Test pre-data");
			return ResponseEntity.ok(allData);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve weight and TB test data", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
