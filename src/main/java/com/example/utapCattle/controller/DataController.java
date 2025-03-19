package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.AllDataDto;
import com.example.utapCattle.service.DataService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = { "http://localhost:4200", "http://35.178.210.158" }, allowCredentials = "true")
public class DataController extends BaseController {

	private final DataService dataService;

	public DataController(DataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * Endpoint to retrieve all necessary data for the CattleManagement application on startup.
	 *
	 * @return An {@link AllDataDto} object containing all the required data
	 */
	@PostMapping()
	public ResponseEntity<AllDataDto> getAllData(HttpServletRequest request) {
		try {
			Long userId = Long.parseLong(request.getHeader("User-ID"));
			final AllDataDto allData = dataService.getAllData(userId);
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
	public ResponseEntity<AllDataDto> getMedicalConditionData(HttpServletRequest request) {
		try {
			Long userId = Long.parseLong(request.getHeader("User-ID"));
			Long farmId = Long.parseLong(request.getHeader("Farm-ID"));
			final AllDataDto allData = dataService.getMedicalConditionData(userId, farmId);
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
	public ResponseEntity<AllDataDto> getTreatmentData(HttpServletRequest request) {
		try {
			Long userId = Long.parseLong(request.getHeader("User-ID"));
			Long farmId = Long.parseLong(request.getHeader("Farm-ID"));
			final AllDataDto allData = dataService.getTreatmentData(userId, farmId);
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
	public ResponseEntity<AllDataDto> getTBTestData(HttpServletRequest request) {
		try {
			Long userId = Long.parseLong(request.getHeader("User-ID"));
			Long farmId = Long.parseLong(request.getHeader("Farm-ID"));
			final AllDataDto allData = dataService.getWeightAndTBTestData(userId, farmId);
			logger.info("Retrieved Weigtht and TB Test pre-data");
			return ResponseEntity.ok(allData);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve weight and TB test data", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "/sales")
	public AllDataDto getSalesData(HttpServletRequest request) {
		logger.info("Incoming request: Retrieving Sales pre-data");
		Long userId = Long.parseLong(request.getHeader("User-ID"));
		Long farmId = Long.parseLong(request.getHeader("Farm-ID"));
		final AllDataDto allData = dataService.getSalesData(userId, farmId);
		logger.info("Request successful: Retrieved Sales pre-data");
		return allData;
	}

	@GetMapping(value = "/register-user")
	public AllDataDto getRegisterUserData() {
		logger.info("Incoming request: Registering data pre-data");
		final AllDataDto allData = dataService.getRegisterUserData();
		logger.info("Request successful: Retrieved Sales pre-data");
		return allData;
	}

	@GetMapping(value = "/filter")
	public AllDataDto getFilterData() {
		logger.info("Incoming request: Retrieving Sales pre-data");
		final AllDataDto allData = dataService.getFilterData();
		logger.info("Request successful: Retrieved Sales pre-data");
		return allData;
	}

	@GetMapping(value = "/slaughter-market")
	public AllDataDto getSlaughtersHouse() {
		logger.info("Incoming request: Retrieving Slaughter House pre-data");
		final AllDataDto allData = dataService.getSlaughtersHouse();
		logger.info("Request successful: Retrieved Sales pre-data");
		return allData;
	}
}
