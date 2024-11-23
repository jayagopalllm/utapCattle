package com.example.utapCattle.service;

import java.util.List;
import java.util.Map;

import com.example.utapCattle.model.dto.MovementDto;
import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.dto.WeightHistoryDto;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;

public interface TreatmentHistoryService {

	List<TreatmentHistoryDto> getAllTreatmentHistory();

	List<TreatmentHistoryDto> getTreatmentHistoriesByEId(final Long id);


	/**
	 * Saves the treatment history records and related information.
	 * 
	 * @param treatmentHistoryMetadata The metadata containing treatment history
	 *                                 records and additional information.
	 * @return A map that contains {@link TreatmentHistoryDto}, {@link Comment},
	 *         {@link WeightHistoryDto}, {@link MovementDto} representing the saved
	 *         treatment histories.
	 * @throws IllegalArgumentException if any mandatory fields are missing in the
	 *                                  treatment history records.
	 */
	public Map<String, Object> saveTreatmentHistory(final TreatmentHistoryMetadata treatmentHistoryMetadata);

	public Map<String, Object> getCattleDetailsAndAverageConditionScore(final String earTagOrEId) throws Exception;
}
