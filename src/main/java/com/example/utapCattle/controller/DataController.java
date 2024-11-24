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
	 * Retrieves all data required for populating the dropdown list on the passport page.
	 *
	 * <p>This endpoint is used as a lookup to populate various dropdowns on the UI.
	 * It fetches data via the {@code dataService.getAllData()} method. The retrieved data
	 * is encapsulated in an {@link AllDataDto} object.
	 *
	 * @return a {@link ResponseEntity} containing:
	 *         <ul>
	 *           <li>The {@link AllDataDto} object with the retrieved data, and an HTTP 200 (OK) status code if successful.</li>
	 *           <li>An HTTP 500 (Internal Server Error) status code if an error occurs during data retrieval.</li>
	 *         </ul>
	 * @see AllDataDto
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
	 * Retrieves medical condition data required for the induction page.
	 *
	 * <p>This endpoint populates the following dropdowns and lookup fields on the induction page:
	 * <ul>
	 *   <li><b>medicalCondition</b> - List of medical conditions available.</li>
	 *   <li><b>medication</b> - List of medications.</li>
	 *   <li><b>earTagList</b> - List of ear tags with incomplete induction.</li>
	 *   <li><b>pens</b> - List of available pens.</li>
	 *   <li><b>defaultTreatments</b> - List of default treatments.</li>
	 *   <li><b>eidList</b> - List of EIDs with incomplete induction.</li>
	 * </ul>
	 *
	 * @return a {@link ResponseEntity} containing an {@link AllDataDto} object with all the required data,
	 *         and an HTTP status code:
	 *         <ul>
	 *           <li>200 (OK): If the data is successfully retrieved.</li>
	 *           <li>500 (Internal Server Error): If an error occurs during data retrieval.</li>
	 *         </ul>
	 * @see AllDataDto
	 */
	@GetMapping(value = "/condition")
	public ResponseEntity<AllDataDto> getInductionData() {
		try {
			final AllDataDto allData = dataService.getInductionData();
			logger.info("Retrieved medical condition data");
			return ResponseEntity.ok(allData);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve medical condition data", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

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

	@GetMapping(value = "/weight-tb")
	public ResponseEntity<AllDataDto> getTBTestData() {
		try {
			final AllDataDto allData = dataService.getWeightAndTBTestData();
			logger.info("Retrieved Weight and TB Test pre-data");
			return ResponseEntity.ok(allData);
		} catch (final Exception e) {
			logger.error("Exception occurred: Unable to retrieve weight and TB test data", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "/sales")
	public AllDataDto getSalesData() {
		logger.info("Incoming request: Retrieving Sales pre-data");
		final AllDataDto allData = dataService.getSalesData();
		logger.info("Request successful: Retrieved Sales pre-data");
		return allData;
	}
}
