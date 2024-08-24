package com.example.utapCattle.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.entity.TreatmentHistory;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.TreatmentHistoryService;
import com.example.utapCattle.service.repository.TreatmentHistoryRepository;

@Service
public class TreatmentHistoryServiceImpl implements TreatmentHistoryService {

	@Autowired
	private TreatmentHistoryRepository treatmentHistoryRepository;

	@Override
	public List<TreatmentHistoryDto> getAllTreatmentHistory() {
		return treatmentHistoryRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public TreatmentHistoryDto getTreatmentHistoryById(Long id) {
		final Optional<TreatmentHistory> induction = treatmentHistoryRepository.findById(id);
		return induction.map(this::mapToDto).orElse(null);
	}

	@Override
	public List<TreatmentHistoryDto> saveTreatmentHistory(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		// Extract the list of treatment histories from the metadata
		final List<TreatmentHistory> treatmentHistories = treatmentHistoryMetadata.getTreatmentHistories();

		// Validate that all mandatory fields are present.
		validateInductionVO(treatmentHistories);

		final List<Comments> commentList = new ArrayList<>();
		Long commentId = null;
		if (StringUtils.isNotEmpty(treatmentHistoryMetadata.getComment())) {
			commentId = 1L;
//			commentList.add()
		}

		final Date currentDate = new Date();
		// Specify the date format
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// Format the date as a string
		final String formattedDate = formatter.format(currentDate);

		// Set the formatted date string to treatmentDate field

		for (final TreatmentHistory treatmentHistory : treatmentHistories) {
			final Long id = treatmentHistoryRepository.getNextSequenceValue();
			treatmentHistory.setTreatmentHistoryId(id);
			treatmentHistory.setTreatmentDate(formattedDate);
			treatmentHistory.setCattleId(treatmentHistoryMetadata.getCattleId());
			treatmentHistory.setCommentId(commentId);

			Long conditionCommentId = null;
			if (StringUtils.isNotEmpty(treatmentHistory.getConditionComment())) {
				conditionCommentId = 1L;
//				commentList.add()
			}
			treatmentHistory.setConditionCommentId(conditionCommentId);
		}
		// Store all the Treatment History information
		final List<TreatmentHistory> savedTreatmentHistory = treatmentHistoryRepository.saveAll(treatmentHistories);

		// Convert to DTO and add to the list of DTOs
		final List<TreatmentHistoryDto> savedTreatmentHistoryDtos = mapToDto(savedTreatmentHistory);

		if (CollectionUtils.isNotEmpty(commentList)) {
			/*
			 * final Comment comment = new Comment();
			 * comment.setTreatmentHistoryId(savedTreatmentHistory.getTreatmentHistoryId());
			 * comment.setCommentText(treatmentHistoryMetadata.getComment());
			 * commentRepository.save(comment);
			 */
		}

		// Store Weight History information if Record Weight flag is true
		if (Boolean.TRUE.equals(treatmentHistoryMetadata.getRecordWeight())) {
			// Logic to save the weight history
			/*
			 * final WeightHistory weightHistory = new WeightHistory();
			 * weightHistory.setTreatmentHistoryId(savedTreatmentHistory.
			 * getTreatmentHistoryId());
			 * weightHistory.setWeight(treatmentHistory.getWeight());
			 * weightHistoryRepository.save(weightHistory);
			 */
		}

		// Store Movement information if Record Movement flag is true
		if (Boolean.TRUE.equals(treatmentHistoryMetadata.getRecordMovement())) {
			// Logic to save the movement information
			/*
			 * final Movement movement = new Movement();
			 * movement.setTreatmentHistoryId(savedTreatmentHistory.getTreatmentHistoryId())
			 * ; movement.setMovementDetails(treatmentHistory.getMovementDetails());
			 * movementRepository.save(movement);
			 */
		}
		return savedTreatmentHistoryDtos;
	}

	public void validateInductionVO(List<TreatmentHistory> treatmentHistories) {
		for (final TreatmentHistory treatmentHistory : treatmentHistories) {
			// Validate that all mandatory fields are present.
			if (StringUtils.isEmpty(treatmentHistory.getMedicalConditionId())) {
				throw new IllegalArgumentException(
						"MedicalConditionId is a mandatory field and cannot be null or empty.");
			}
			if (treatmentHistory.getMedicationId() == null) {
				throw new IllegalArgumentException("MedicationId is a mandatory field and cannot be null.");
			}
			if (treatmentHistory.getConditionScore() == null) {
				throw new IllegalArgumentException("ConditionScore is a mandatory field and cannot be null.");
			}
			if (StringUtils.isEmpty(treatmentHistory.getBatchNumber())) {
				throw new IllegalArgumentException("BatchNumber is a mandatory field and cannot be null or empty.");
			}
		}

	}

	private List<TreatmentHistoryDto> mapToDto(List<TreatmentHistory> savedTreatmentHistory) {
		return savedTreatmentHistory.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	private TreatmentHistoryDto mapToDto(TreatmentHistory treatmentHistory) {
		return new TreatmentHistoryDto(treatmentHistory.getTreatmentHistoryId(), treatmentHistory.getCattleId(),
				treatmentHistory.getUserId(), treatmentHistory.getMedicalConditionId(),
				treatmentHistory.getMedicationId(), treatmentHistory.getBatchNumber(),
				treatmentHistory.getTreatmentDate(), treatmentHistory.getCommentId(),
				treatmentHistory.getConditionCommentId());
	}

}
