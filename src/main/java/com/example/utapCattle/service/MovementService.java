package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.MovementDto;
import com.example.utapCattle.model.entity.Movement;

public interface MovementService {

	/**
	 * Save Movement information.
	 *
	 * @param movement The Movement entity containing pen details.
	 * @return The saved MovementDto.
	 */
	MovementDto saveMovement(final Movement movement);

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

	/**
	 * Finds and retrieves a list of cattle IDs that are currently located in a
	 * specified pen.
	 *
	 * @param penId The ID of the pen from which to retrieve the cattle IDs.
	 * @return A list of cattle IDs that are associated with the specified pen. If
	 *         no cattle are found, an empty list will be returned.
	 * @throws IllegalArgumentException If the provided penId is null or invalid.
	 * @throws DataAccessException      If there is an issue with the database query
	 *                                  or connection.
	 */
	public List<Long> findCattleIdsByPenId(final Long penId);
}
