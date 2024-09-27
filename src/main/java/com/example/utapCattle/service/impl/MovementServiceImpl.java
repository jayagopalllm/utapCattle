package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.MovementDto;
import com.example.utapCattle.model.entity.Movement;
import com.example.utapCattle.service.MovementService;
import com.example.utapCattle.service.repository.MovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementServiceImpl implements MovementService {

	private final MovementRepository movementRepository;

	MovementServiceImpl(MovementRepository movementRepository) {
		this.movementRepository = movementRepository;
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

}
