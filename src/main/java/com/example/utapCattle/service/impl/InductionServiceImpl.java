package com.example.utapCattle.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.entity.Cattle;
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

	/*
	 * {@inheritDoc}
	 */
	@Override
	public final Map<String, Object> saveInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		// Step 1: Validate that EId and EarTag are present in the input treatment
		// history records.
		validateInductionVO(treatmentHistoryMetadata);

		// add process id
		treatmentHistoryMetadata.setProcessId(1L);

		// Step 2: Store each treatment history record using the
		// TreatmentHistoryServiceImpl
		final Map<String, Object> savedTreatmentHistoryDtos = treatmentHistoryService
				.saveTreatmentHistory(treatmentHistoryMetadata);

		// Step 3: Link the Cattle EId and EarTag, and update the 'isInductionCompleted'
		// flag for the Cattle record.
		updateCattleId(treatmentHistoryMetadata);

		// Step 4: Convert the saved treatment history to DTO and return.
		return savedTreatmentHistoryDtos; // Assuming you want to return the first saved record as DTO
	}

	private void validateInductionVO(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		if (treatmentHistoryMetadata.getCattleId() == null) {
			throw new IllegalArgumentException("EId is a mandatory field and cannot be null or empty.");
		}
		if (treatmentHistoryMetadata.getEarTag() == null || treatmentHistoryMetadata.getEarTag().isEmpty()) {
			throw new IllegalArgumentException("EarTag is a mandatory field and cannot be null or empty.");
		}
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
