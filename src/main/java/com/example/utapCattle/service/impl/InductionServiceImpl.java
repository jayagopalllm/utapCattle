package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.TreatmentHistory;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.InductionService;
import com.example.utapCattle.service.TreatmentHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;

@Service
public class InductionServiceImpl implements InductionService {

	@Autowired
	private CattleRepository cattleRepository;

	@Autowired
	private TreatmentHistoryService treatmentHistoryService;

	/**
	 * Saves the treatment history information after performing necessary
	 * validations, and updates the corresponding cattle record to mark induction as
	 * completed.
	 *
	 * <ul>
	 * <li>Validates the mandatory fields: EId and EarTag.</li>
	 * <li>Uses the {@link TreatmentHistoryServiceImpl#saveTreatmentHistory} method
	 * to save each treatment history record.</li>
	 * <li>Links the Cattle EId and EarTag, ensuring consistency in the Cattle
	 * entity.</li>
	 * <li>Marks the cattle record as having completed induction by setting the
	 * 'isInductionCompleted' flag to true.</li>
	 * </ul>
	 *
	 * @param treatmentHistoryList A list of {@link TreatmentHistory} entities
	 *                             representing the treatment history details to be
	 *                             saved.
	 * @return A {@link TreatmentHistoryDto} object representing the saved treatment
	 *         history data.
	 * @throws IllegalArgumentException if EId or EarTag is missing or invalid.
	 */
	@Override
	public final List<TreatmentHistoryDto> saveInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		// Step 1: Validate that EId and EarTag are present in the input treatment
		// history records.
		if (treatmentHistoryMetadata.getCattleId() == null) {
			throw new IllegalArgumentException("EId is a mandatory field and cannot be null or empty.");
		}
		if (treatmentHistoryMetadata.getEarTag() == null || treatmentHistoryMetadata.getEarTag().isEmpty()) {
			throw new IllegalArgumentException("EarTag is a mandatory field and cannot be null or empty.");
		}

		// Step 2: Store each treatment history record using the
		// TreatmentHistoryServiceImpl
		final List<TreatmentHistoryDto> savedTreatmentHistoryDtos = treatmentHistoryService
				.saveTreatmentHistory(treatmentHistoryMetadata);

		// Step 3: Link the Cattle EId and EarTag, and update the 'isInductionCompleted'
		// flag for the Cattle record.
		updateCattleId(treatmentHistoryMetadata);

		// Step 4: Convert the saved treatment history to DTO and return.
		return savedTreatmentHistoryDtos; // Assuming you want to return the first saved record as DTO
	}

	private void updateCattleId(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		// Find the Cattle record by earTag
		final Optional<Cattle> existingCattle = cattleRepository.findByEarTag(treatmentHistoryMetadata.getEarTag());

		if (existingCattle.isPresent()) {
			// Update the cattleId with eid
			final Cattle cattle = existingCattle.get();
			cattle.setCattleId(Long.valueOf(treatmentHistoryMetadata.getCattleId()));
			cattle.setIsInductionCompleted(true);
			cattleRepository.save(cattle);
		} else {
			// Handle case where Cattle with the given earTag does not exist
			throw new IllegalArgumentException(
					"No Cattle record found with the given EarTag: " + treatmentHistoryMetadata.getEarTag());
		}

	}

}
