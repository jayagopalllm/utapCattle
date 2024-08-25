package com.example.utapCattle.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.WeightHistoryDto;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.WeightHistoryService;
import com.example.utapCattle.service.repository.WeightHistoryRepository;

@Service
public class WeightHistoryServiceImpl implements WeightHistoryService {

	private final WeightHistoryRepository weightHistoryRepository;

	@Autowired
	public WeightHistoryServiceImpl(WeightHistoryRepository weightHistoryRepository) {
		this.weightHistoryRepository = weightHistoryRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WeightHistoryDto saveWeightHistory(WeightHistory weightHistory) {
		weightHistory.setWeightHistoryId(getNextSequenceValue());
		// Save the weight history entity
		final WeightHistory savedWeightHistory = weightHistoryRepository.save(weightHistory);

		// Convert the saved entity to DTO
		return mapToDto(savedWeightHistory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getNextSequenceValue() {
		return weightHistoryRepository.getNextSequenceValue();
	}

	private WeightHistoryDto mapToDto(WeightHistory weightHistory) {
		return new WeightHistoryDto(weightHistory.getWeightHistoryId(), weightHistory.getCattleId(),
				weightHistory.getWeightDateTime(), weightHistory.getWeight(), weightHistory.getUserId());
	}

}
