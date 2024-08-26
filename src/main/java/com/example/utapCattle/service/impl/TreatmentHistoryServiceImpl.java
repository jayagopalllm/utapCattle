package com.example.utapCattle.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.MovementDto;
import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.dto.WeightHistoryDto;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.model.entity.Movement;
import com.example.utapCattle.model.entity.TreatmentHistory;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.CommentService;
import com.example.utapCattle.service.MovementService;
import com.example.utapCattle.service.TreatmentHistoryService;
import com.example.utapCattle.service.WeightHistoryService;
import com.example.utapCattle.service.repository.TreatmentHistoryRepository;

@Service
public class TreatmentHistoryServiceImpl implements TreatmentHistoryService {

	@Autowired
	private TreatmentHistoryRepository treatmentHistoryRepository;

	@Autowired
	private CommentService commentService;

	@Autowired
	private WeightHistoryService weightHistoryService;

	@Autowired
	private MovementService movementService;

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
	public Map<String, Object> saveTreatmentHistory(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		// Extract the list of treatment histories from the metadata
		final List<TreatmentHistory> treatmentHistories = treatmentHistoryMetadata.getTreatmentHistories();

		// Validate that all mandatory fields are present.
		validateInductionVO(treatmentHistories);

		// Get the current date in the required format
		final String formattedDate = getCurrentFormattedDate();

		final Long cattleId = treatmentHistoryMetadata.getCattleId();
		final Long processId = treatmentHistoryMetadata.getProcessId();

		final List<Comment> commentList = new ArrayList<>();
		final Long commentId = createTreatmentComment(treatmentHistoryMetadata.getComment(), cattleId, processId, null,
				formattedDate, commentList);

		// Update treatment histories with additional information
		updateTreatmentHistories(treatmentHistories, formattedDate, cattleId, processId, commentId, commentList);

		// Store all the Treatment History information
		final List<TreatmentHistory> savedTreatmentHistory = treatmentHistoryRepository.saveAll(treatmentHistories);

		final Map<String, Object> outputMap = new HashMap<>();

		// Convert to DTO and add to the list of DTOs
		final List<TreatmentHistoryDto> savedTreatmentHistoryDtos = mapToDto(savedTreatmentHistory);
		outputMap.put("treatmentHistories", savedTreatmentHistoryDtos);

		if (CollectionUtils.isNotEmpty(commentList)) {
			commentService.saveComments(commentList);
			outputMap.put("comments", commentList);
		}

		// Store Weight History information if Record Weight flag is true
		if (Boolean.TRUE.equals(treatmentHistoryMetadata.getRecordWeight())) {
			final WeightHistoryDto weightHistoryDto = saveWeightHistory(cattleId, formattedDate,
					treatmentHistoryMetadata.getWeight());
			outputMap.put("weightHistories", weightHistoryDto);
		}

		// Store Movement information if Record Movement flag is true
		if (Boolean.TRUE.equals(treatmentHistoryMetadata.getRecordMovement())) {
			final MovementDto movementDto = saveMovement(cattleId, treatmentHistoryMetadata.getPen(), formattedDate);
			outputMap.put("movements", movementDto);
		}

		return outputMap;
	}

	/**
	 * Returns the current date formatted as a string in the format "yyyy-MM-dd".
	 *
	 * @return The formatted current date.
	 */
	private String getCurrentFormattedDate() {
		final Date currentDate = new Date();
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(currentDate);
	}

	/**
	 * Updates the treatment histories with additional information such as IDs and
	 * comments.
	 *
	 * @param treatmentHistories The list of treatment histories to update.
	 * @param formattedDate      The formatted current date.
	 * @param cattleId           The ID of the cattle.
	 * @param processId          The process ID.
	 * @param commentId          The comment ID.
	 */
	private void updateTreatmentHistories(final List<TreatmentHistory> treatmentHistories, final String formattedDate,
			final Long cattleId, final Long processId, final Long commentId, final List<Comment> commentList) {
		for (final TreatmentHistory treatmentHistory : treatmentHistories) {
			final Long id = treatmentHistoryRepository.getNextSequenceValue();
			treatmentHistory.setTreatmentHistoryId(id);
			treatmentHistory.setTreatmentDate(formattedDate);
			treatmentHistory.setCattleId(cattleId);
			treatmentHistory.setProcessId(processId);
			treatmentHistory.setCommentId(commentId);

			final Long conditionCommentId = createTreatmentComment(treatmentHistory.getConditionComment(), cattleId,
					processId, id, formattedDate, commentList);
			treatmentHistory.setConditionCommentId(conditionCommentId);
		}
	}

	/**
	 * Creates a treatment comment if the provided comment text is not empty and
	 * adds it to the provided list of comments.
	 * 
	 * This method generates a new comment ID, creates a {@link Comment} object with
	 * the provided details, and adds it to the list of comments. The comment ID is
	 * retrieved using the {@link CommentService#getNextSequenceValue()} method.
	 * 
	 * @param comment       The text of the comment to create. If this is null or
	 *                      empty, no comment will be created.
	 * @param cattleId      The ID of the cattle associated with the comment.
	 * @param processId     The process ID associated with the comment.
	 * @param entityId      The ID of the entity (e.g., treatment history) related
	 *                      to the comment. This can be null if not applicable.
	 * @param formattedDate The date when the comment is created, formatted as a
	 *                      string.
	 * @param commentList   The list to which the created comment will be added.
	 *                      This list is modified by the method.
	 * @return The ID of the created comment, or null if no comment is created
	 *         (i.e., if the provided comment text is empty).
	 */
	private Long createTreatmentComment(final String comment, final Long cattleId, final Long processId,
			final Long entityId, final String formattedDate, final List<Comment> commentList) {
		if (StringUtils.isEmpty(comment)) {
			return null;
		}
		final Long commentId = commentService.getNextSequenceValue();

		final Comment treatmentComment = new Comment(commentId, comment, processId, cattleId, null, entityId,
				formattedDate);

		commentList.add(treatmentComment);
		return commentId;
	}

	/**
	 * Saves the weight history information if the Record Weight flag is true.
	 *
	 * @param cattleId      The ID of the cattle.
	 * @param formattedDate The formatted current date.
	 * @param weight        The weight to save.
	 * @return {@link WeightHistoryDto}
	 */
	private WeightHistoryDto saveWeightHistory(final Long cattleId, final String formattedDate, final Double weight) {
		final WeightHistory weightHistory = new WeightHistory();
		weightHistory.setCattleId(cattleId);
		weightHistory.setWeightDateTime(formattedDate);
		weightHistory.setWeight(weight);

		return weightHistoryService.saveWeightHistory(weightHistory);
	}

	/**
	 * Saves the movement information if the Record Movement flag is true.
	 *
	 * @param cattleId      The ID of the cattle.
	 * @param penId         The ID of the pen.
	 * @param formattedDate The formatted current date.
	 * @return {@link MovementDto}
	 */
	private MovementDto saveMovement(final Long cattleId, final Integer penId, final String formattedDate) {
		final Movement movement = new Movement();
		movement.setCattleId(cattleId);
		movement.setPenId(penId);
		movement.setMovementDate(formattedDate);

		return movementService.saveMovement(movement);
	}

	private void validateInductionVO(List<TreatmentHistory> treatmentHistories) {
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
