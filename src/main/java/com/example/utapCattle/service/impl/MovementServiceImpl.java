package com.example.utapCattle.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.MovementDto;
import com.example.utapCattle.model.entity.Movement;
import com.example.utapCattle.service.MovementService;
import com.example.utapCattle.service.repository.MovementRepository;

@Service
public class MovementServiceImpl implements MovementService {

	@Autowired
	private MovementRepository movementRepository;

	@Override
	public MovementDto saveMovement(final Movement movement) {
		movement.setMovementId(getNextSequenceValue());
		final Movement savedMovement = movementRepository.save(movement);
		return mapToDto(savedMovement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getNextSequenceValue() {
		return movementRepository.getNextSequenceValue();
	}

	private MovementDto mapToDto(final Movement savedMovement) {
		return new MovementDto(savedMovement.getMovementId(), savedMovement.getCattleId(), savedMovement.getPenId(),
				savedMovement.getMovementDate(), savedMovement.getUserId(), savedMovement.getComment());
	}

}
