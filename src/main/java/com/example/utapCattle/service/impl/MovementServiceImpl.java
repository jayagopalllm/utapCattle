package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.MovementDto;
import com.example.utapCattle.model.entity.CtsMovement;
import com.example.utapCattle.model.entity.Movement;
import com.example.utapCattle.service.MovementService;
import com.example.utapCattle.service.repository.CtsMovementRepository;
import com.example.utapCattle.service.repository.MovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class MovementServiceImpl implements MovementService {

	private final MovementRepository movementRepository;
	private final CtsMovementRepository ctsMovementRepository;

	public MovementServiceImpl(MovementRepository movementRepository,CtsMovementRepository ctsMovementRepository) {
		this.movementRepository = movementRepository;
		this.ctsMovementRepository = ctsMovementRepository;
	}
	private static final Map<String, String> MOVEMENT_TYPE_MAP = new HashMap<>();
	private static final Map<String, String> MOVEMENT_DIRECTION_MAP = new HashMap<>();

	static {
		MOVEMENT_TYPE_MAP.put("0", "birth");
		MOVEMENT_TYPE_MAP.put("2", "On");
		MOVEMENT_TYPE_MAP.put("3", "Off");
		MOVEMENT_TYPE_MAP.put("7", "death");
		MOVEMENT_TYPE_MAP.put("72", "through On/Off");
		MOVEMENT_TYPE_MAP.put("73", "through Off/On");
		MOVEMENT_TYPE_MAP.put("77", "through On/Death");
	}

	static {
		MOVEMENT_DIRECTION_MAP.put("1", "On type movement");
		MOVEMENT_DIRECTION_MAP.put("2", "Off type movement");
	}


	@Override
	public MovementDto saveMovement(final Movement movement) {
		movement.setMovementId(getNextSequenceValue());
		final Movement savedMovement = movementRepository.save(movement);
		return mapToDto(savedMovement);
	}

	@Override
	public Long getNextSequenceValue() {
		return movementRepository.getNextSequenceValue();
	}

	private MovementDto mapToDto(final Movement savedMovement) {
		return new MovementDto(savedMovement.getMovementId(), savedMovement.getCattleId(), savedMovement.getPenId(),
				savedMovement.getMovementDate(), savedMovement.getUserId(), savedMovement.getComment());
	}

	@Override
	public List<Long> findCattleIdsByPenId(final Long penId) {
		return movementRepository.findCattleIdsByPenId(penId);
	}

	@Override
	public Long saveMovementFileData(MultipartFile file) {
		try {
			// Parse the CSV file and extract data
			List<CtsMovement> movementRecords = parseCsvFile(file);

			// Save the extracted records into the database
			List<CtsMovement> savedRecords = ctsMovementRepository.saveAll(movementRecords);

			// Return the number of records saved
			return (long) savedRecords.size();
		} catch (Exception e) {
			throw new RuntimeException("Failed to process and save movement file data", e);
		}
	}

	private List<CtsMovement> parseCsvFile(MultipartFile file) throws Exception {
		List<CtsMovement> movementRecords = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] columns = line.split(","); // Assuming CSV columns are comma-separated

				// Determine the record type from Column B
				String recordType = columns[1].trim(); // Assuming Column B is at index 1

				// Process only A (Animal Details) and M (Movement) records
				if (recordType.equals("A") || recordType.trim().split(" =")[0].equals("M")) {
					// Generate a hash for the row based on cumulative data
					String hash = generateHashForRow(columns);

					// Check if the hash already exists in the database
					if (ctsMovementRepository.existsByHashkey(hash)) {
						System.out.println("Skipping duplicate row with hash: " + hash);
						continue; // Skip this row
					}

					// Create a CtsMovement object based on the record type
					CtsMovement movement = recordType.equals("A")
							? parseAnimalDetails(columns)
							: parseMovementDetails(columns);

					// Set the hashkey in the entity
					movement.setHashkey(hash);

					// Add the entity to the list
					movementRecords.add(movement);
				}
			}
		}

		return movementRecords;
	}
	private CtsMovement parseAnimalDetails(String[] columns) {
		CtsMovement movement = new CtsMovement();

		// Populate fields based on the Animal Details record structure
		movement.setCategory(columns[1].trim());
		movement.setSlNumber(columns[2].trim()); // Sequence number (Column C)
		movement.setIdentityType(extractIdentityType(columns[3])); // Identity type (Column D)
		movement.setEartagId(columns[4].trim()); // Official animal identity (Column E)
		movement.setCtsIdentity(columns[5].trim()); // Current identity type (Column F)
		movement.setCtsEartag(columns[6].trim()); // Current animal identity (Column G)
		movement.setDateOfBirth(columns[7].trim()); // Date of birth (Column H)
		movement.setSex(columns[8].trim()); // Animal sex (Column I)
		movement.setBreed(columns[9].trim()); // Breed code (Column J)
		movement.setRecordedIdentityOfGeneticDam(columns[10].trim()); // Genetic dam (Column K)
		movement.setCol19(columns[11].trim()); // Surrogate dam (Column L)
		movement.setIdentityOfSire(columns[12].trim()); // Sire (Column M)
		movement.setVersionOfPassport(columns[13].trim()); // Passport version (Column N)

		return movement;
	}

	private CtsMovement parseMovementDetails(String[] columns) {
		CtsMovement movement = new CtsMovement();

		// Populate fields based on the Movement record structure
		movement.setCategory(columns[1].trim().split(" =")[0]);
		movement.setSlNumber(columns[2].trim()); // Sequence number (Column C)
		movement.setIdentityType(extractIdentityType(columns[3])); // Identity type (Column D)
		movement.setEartagId(columns[4].trim()); // Official animal identity (Column E)
		movement.setCtsIdentity(columns[5].trim()); // Movement identity type (Column F)
		movement.setCtsEartag(columns[6].trim()); // Current animal identity (Column G)
		movement.setDateOfMovement(columns[9].trim()); // Date of movement (Column J)
		movement.setMovementType(mapMovementType(columns[10].trim())); // Movement type (Column K)
		movement.setMovementDirection(mapMovementDirection(columns[11].trim())); // Movement direction (Column L)
		movement.setTypeOfHolding(columns[12].trim()); // Type of holding (Column M)
		movement.setLocationOrHoldingCph(columns[13].trim()); // Location CPH (Column N)
		movement.setExternalSubLocation(columns[14].trim()); // External sub-location (Column O)
		movement.setMovementReceiptDate(columns[15].trim()); // Movement receipt date (Column P)

		return movement;
	}

	private String extractIdentityType(String identityTypeColumn) {
		if (identityTypeColumn == null || identityTypeColumn.isEmpty()) {
			return null;
		}

		// Split the string at '=' and take the first part
		String[] parts = identityTypeColumn.split("=");
		return parts[0].trim(); // Return the first part, e.g., "ET"
	}

	private String mapMovementDirection(String movementDirectionCode) {
		if (movementDirectionCode == null || movementDirectionCode.isEmpty()) {
			return null; // Return null or handle as required for empty values
		}
		return MOVEMENT_DIRECTION_MAP.getOrDefault(movementDirectionCode.trim(), "Unknown");
	}

	private String mapMovementType(String movementTypeCode) {
		if (movementTypeCode == null || movementTypeCode.isEmpty()) {
			return null; // Return null or handle as required for empty values
		}
		return MOVEMENT_TYPE_MAP.getOrDefault(movementTypeCode.trim(), "Unknown");
	}

	private String generateHashForRow(String[] columns) {
		try {
			// Concatenate all column values into a single string
			String rowData = String.join(",", columns);

			// Generate SHA-256 hash of the row data
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(rowData.getBytes(StandardCharsets.UTF_8));

			// Convert the hash to a Base64-encoded string
			return Base64.getEncoder().encodeToString(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error generating hash for row", e);
		}
	}

}
