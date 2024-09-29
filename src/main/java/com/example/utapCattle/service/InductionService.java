package com.example.utapCattle.service;

import java.util.Map;

import com.example.utapCattle.exception.CommentValidationException;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.model.entity.TreatmentHistory;
import com.example.utapCattle.service.impl.TreatmentHistoryServiceImpl;

public interface InductionService {

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
	 * @return A object representing the saved treatment history data.
	 * @throws IllegalArgumentException if EId or EarTag is missing or invalid.
	 */
	public Map<String, Object> saveInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata) throws CommentValidationException;
}
