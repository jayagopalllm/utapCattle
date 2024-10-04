package com.example.utapCattle.service.impl;

import com.example.utapCattle.mapper.MovementMapper;
import com.example.utapCattle.model.dto.MovementDto;
import com.example.utapCattle.model.entity.Movement;
import com.example.utapCattle.service.MovementService;
import com.example.utapCattle.service.repository.MovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementServiceImpl implements MovementService {

	private final MovementRepository movementRepository;

	private final MovementMapper mapper;

	public MovementServiceImpl(MovementRepository movementRepository, MovementMapper mapper) {
		this.movementRepository = movementRepository;
		this.mapper = mapper;
	}

	@Override
	public MovementDto saveMovement(final Movement movement) {
		movement.setMovementId(getNextSequenceValue());
		final Movement savedMovement = movementRepository.save(movement);
		return mapper.toDto(savedMovement);
	}

	@Override
	public Long getNextSequenceValue() {
		return movementRepository.getNextSequenceValue();
	}

	@Override
	public List<Long> findCattleIdsByPenId(final Long penId) {
		return movementRepository.findCattleIdsByPenId(penId);
	}

}
