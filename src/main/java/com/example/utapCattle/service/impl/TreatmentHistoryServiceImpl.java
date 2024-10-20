package com.example.utapCattle.service.impl;

import com.example.utapCattle.exception.CommentException;
import com.example.utapCattle.exception.TreatmentHistoryException;
import com.example.utapCattle.mapper.TreatmentHistoryMapper;
import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.dto.MovementDto;
import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.dto.TreatmentHistoryMetadataDto;
import com.example.utapCattle.model.dto.WeightHistoryDto;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.model.entity.Movement;
import com.example.utapCattle.model.entity.TreatmentHistory;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.CattleService;
import com.example.utapCattle.service.CommentService;
import com.example.utapCattle.service.MovementService;
import com.example.utapCattle.service.TreatmentHistoryService;
import com.example.utapCattle.service.WeightHistoryService;
import com.example.utapCattle.service.repository.TreatmentHistoryRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TreatmentHistoryServiceImpl implements TreatmentHistoryService {

	private final TreatmentHistoryRepository treatmentHistoryRepository;
	private final CommentService commentService;
	private final WeightHistoryService weightHistoryService;
	private final MovementService movementService;
	private final CattleService cattleService;
	private final TreatmentHistoryMapper mapper;

	public TreatmentHistoryServiceImpl(TreatmentHistoryRepository treatmentHistoryRepository,
									   CommentService commentService,
									   WeightHistoryService weightHistoryService,
									   MovementService movementService,
									   CattleService cattleService,
									   TreatmentHistoryMapper mapper) {
		this.treatmentHistoryRepository = treatmentHistoryRepository;
		this.commentService = commentService;
		this.weightHistoryService = weightHistoryService;
		this.movementService = movementService;
		this.cattleService = cattleService;
		this.mapper = mapper;
	}

	@Override
	public List<TreatmentHistoryDto> getAllTreatmentHistory() {
		return treatmentHistoryRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	@Override
	public List<TreatmentHistoryDto> getTreatmentHistoriesByEId(Long eId) {
		final List<TreatmentHistory> treatmentHistories = treatmentHistoryRepository.findByCattleId(eId);
		return treatmentHistories.stream().map(mapper::toDto).collect(Collectors.toList());
	}

	@Override
	public Map<String, Object> saveTreatmentHistory(final TreatmentHistoryMetadataDto treatmentHistoryMetadataDto) throws CommentException, TreatmentHistoryException {
		final List<TreatmentHistoryDto> treatmentHistories = treatmentHistoryMetadataDto.getTreatmentHistories();
		validateInductionVO(treatmentHistories);

		final String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		final Long cattleId = treatmentHistoryMetadataDto.getCattleId();
		final Long processId = treatmentHistoryMetadataDto.getProcessId();
		final List<Comment> commentList = new ArrayList<>();
		final Long commentId = createTreatmentComment(treatmentHistoryMetadataDto.getComment(), cattleId, processId, null,
				formattedDate, commentList);
		updateTreatmentHistories(treatmentHistories, formattedDate, cattleId, processId, commentId, commentList);

		final List<TreatmentHistory> savedTreatmentHistory = treatmentHistoryRepository.saveAll(treatmentHistories.stream().map(mapper::toEntity).collect(Collectors.toList()));

		final Map<String, Object> outputMap = new HashMap<>();

		final List<TreatmentHistoryDto> savedTreatmentHistoryDtos = savedTreatmentHistory.stream().map(mapper::toDto).collect(Collectors.toList());
		outputMap.put("treatmentHistories", savedTreatmentHistoryDtos);

		if (CollectionUtils.isNotEmpty(commentList)) {
			commentService.saveComments(commentList);
			outputMap.put("comments", commentList);
		}

		if (Boolean.TRUE.equals(treatmentHistoryMetadataDto.getRecordWeight())) {
			final WeightHistoryDto weightHistoryDto = saveWeightHistory(cattleId, formattedDate,
					treatmentHistoryMetadataDto.getWeight());
			outputMap.put("weightHistories", weightHistoryDto);
		}

		if (Boolean.TRUE.equals(treatmentHistoryMetadataDto.getRecordMovement())) {
			final MovementDto movementDto = saveMovement(cattleId, treatmentHistoryMetadataDto.getPen(), formattedDate);
			outputMap.put("movements", movementDto);
		}

		return outputMap;
	}

	@Override
	public Map<String, Object> getCattleDetailsAndAverageConditionScore(String earTagOrEId) throws Exception {
		final CattleDto cattleDto = cattleService.getCattleByEarTag(earTagOrEId);
		if (cattleDto == null) {
			throw new BadRequestException("Cattle not found - " + earTagOrEId);
		}
		if (cattleDto.getCattleId() == null) {
			throw new BadRequestException("Induction not yet completed for cattle - " + earTagOrEId);
		}
		final Map<String, Object> outputMap = new HashMap<>();
		outputMap.put("cattle", cattleDto);
		final Double averageConditionScore = treatmentHistoryRepository
				.findAverageConditionScoreByCattleId(cattleDto.getCattleId());
		outputMap.put("avgConditionScore", averageConditionScore);

		return outputMap;
	}

	private void updateTreatmentHistories(final List<TreatmentHistoryDto> treatmentHistories, final String formattedDate,
			final Long cattleId, final Long processId, final Long commentId, final List<Comment> commentList) {
		for (final TreatmentHistoryDto treatmentHistory : treatmentHistories) {
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

	private Long createTreatmentComment(final String comment, final Long cattleId, final Long processId,
			final Long entityId, final String formattedDate, final List<Comment> commentList) {
		if (StringUtils.isEmpty(comment)) {
			return null;
		}
		final Long commentId = commentService.getNextSequenceValue();
		final Comment treatmentComment = new Comment(commentId, comment, processId, cattleId, null, entityId,
				formattedDate);
		commentList.add(treatmentComment);
		//TODO: unless the comment is persisted the nextsequencevale will always return same
		return commentId;
	}

	private WeightHistoryDto saveWeightHistory(final Long cattleId, final String formattedDate, final Double weight) {
		final WeightHistory weightHistory = new WeightHistory();
		weightHistory.setCattleId(cattleId);
		weightHistory.setWeightDateTime(formattedDate);
		weightHistory.setWeight(weight);

		return weightHistoryService.saveWeightHistory(weightHistory);
	}

	private MovementDto saveMovement(final Long cattleId, final Integer penId, final String formattedDate) {
		final Movement movement = new Movement();
		movement.setCattleId(cattleId);
		movement.setPenId(penId);
		movement.setMovementDate(formattedDate);

		return movementService.saveMovement(movement);
	}

	private void validateInductionVO(List<TreatmentHistoryDto> treatmentHistories) throws TreatmentHistoryException {

		List<String> validationErrors = new ArrayList<>();

		if (CollectionUtils.isEmpty(treatmentHistories)) {
			validationErrors.add("TREATMENT HISTORIES MISSING");
		}
		for (final TreatmentHistoryDto treatmentHistory : treatmentHistories) {
			if (StringUtils.isEmpty(treatmentHistory.getMedicalConditionId())) {
				validationErrors.add(
						"MEDICALCONDITIONID MISSING");
			}
			if (treatmentHistory.getMedicationId() == null) {
				validationErrors.add("MEDICATIONID MISSING");
			}
			if (treatmentHistory.getConditionScore() == null) {
				validationErrors.add("CONDITIONSCORE MISSING");
			}
			if (StringUtils.isEmpty(treatmentHistory.getBatchNumber())) {
				validationErrors.add("BATCHNUMBER MISSING");
			}
			if (!validationErrors.isEmpty()) {
				throw new TreatmentHistoryException(validationErrors);
			}
		}
	}
}
