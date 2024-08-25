package com.example.utapCattle.service;

import com.example.utapCattle.model.dto.WeightHistoryDto;
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
	 * Retrieves the next value from the configured sequence in the database.
	 * <p>
	 * This method is useful for generating unique identifiers for entities that
	 * require a sequential ID. It executes a native query to fetch the next value
	 * from a PostgreSQL sequence.
	 * </p>
	 * 
	 * @return the next value in the sequence as a {@link Long}.
	 * @throws javax.persistence.PersistenceException if there is an issue executing
	 *                                                the query.
	 */
	public Long getNextSequenceValue();
}
