package com.example.utapCattle.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.utapCattle.model.dto.WeightHistoryDto;
import com.example.utapCattle.model.dto.WeightHistoryInfo;
import com.example.utapCattle.model.dto.WeightHistoryProgressDto;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.model.entity.WeightHistory;

public interface WeightHistoryService {

	/**
	 * Save weight history information.
	 *
	 * @param weightHistory The WeightHistory entity containing weight details.
	 * @return The saved WeightHistoryDto.
	 */
	WeightHistoryDto saveWeightHistory(final WeightHistory weightHistory);

	/**
	 * Saves weight history and movement records based on the
	 * TreatmentHistoryMetadata.
	 * 
	 * @param treatmentHistoryMetadata The metadata containing the details to save
	 *                                 weight and movement.
	 */
	void saveWeightAndMovement(final TreatmentHistoryMetadata treatmentHistoryMetadata);

	/**
	 * Retrieves the next value from the configured sequence in the database.
	 * <p>
	 * This method is useful for generating unique identifiers for entities that
	 * require a sequential ID. It executes a native query to fetch the next value
	 * from a PostgreSQL sequence.
	 * </p>
	 * 
	 * @return the next value in the sequence as a {@link Long}.
	 *                                                the query.
	 */
	public Long getNextSequenceValue();

	/**
	 * Retrieves a list of {@link WeightHistoryProgressDto} records for a specific
	 * cattle identified by the given {@code cattleId}, and sorts them in ascending
	 * order based on the weight date and time.
	 *
	 * <p>
	 * This method queries the database to fetch the weight history of the cattle,
	 * ordered by the date and time the weight was recorded. It can be used to track
	 * the weight progress of a specific cattle over time.
	 * </p>
	 *
	 * @param cattleId the ID of the cattle for which the weight history needs to be
	 *                 retrieved
	 * @return a list of {@link WeightHistoryProgressDto} entities representing the
	 *         weight history of the specified cattle, ordered by weight date and
	 *         time
	 * @throws IllegalArgumentException if the cattleId is null
	 * @throws DataAccessException      if there is an error accessing the database
	 */
	List<WeightHistoryProgressDto> deriveWeightHistoryInfoByCattleId(final String cattleId);

	/**
	 * Retrieves the latest weight, last DLWG, and overall DLWG for a given EID.
	 *
	 * @param penId the Pen ID of the cattle
	 * @return List<WeightHistoryInfo> containing the required information
	 */
	List<WeightHistoryInfo> getWeightHistoryByPen(final Long penId);

	List<Object[]> getWeightHistoryForToday();



}
