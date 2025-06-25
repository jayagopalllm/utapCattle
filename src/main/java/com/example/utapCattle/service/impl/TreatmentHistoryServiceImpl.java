package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.*;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.model.entity.Movement;
import com.example.utapCattle.model.entity.TreatmentHistory;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.CattleService;
import com.example.utapCattle.service.CommentService;
import com.example.utapCattle.service.MovementService;
import com.example.utapCattle.service.TreatmentHistoryService;
import com.example.utapCattle.service.WeightHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.MedicationRepository;
import com.example.utapCattle.service.repository.TreatmentHistoryRepository;
import com.example.utapCattle.utils.DateUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TreatmentHistoryServiceImpl implements TreatmentHistoryService {

	private final TreatmentHistoryRepository treatmentHistoryRepository;
	private final MedicationRepository medicationRepository;
	private final CommentService commentService;
	private final WeightHistoryService weightHistoryService;
	private final MovementService movementService;
	private final CattleService cattleService;
	private final CattleRepository cattleRepository;

	public TreatmentHistoryServiceImpl(TreatmentHistoryRepository treatmentHistoryRepository,
			MedicationRepository medicationRepository,
			CommentService commentService,
			WeightHistoryService weightHistoryService,
			MovementService movementService,
			CattleService cattleService,
			CattleRepository cattleRepository) {
		this.treatmentHistoryRepository = treatmentHistoryRepository;
		this.medicationRepository = medicationRepository;
		this.commentService = commentService;
		this.weightHistoryService = weightHistoryService;
		this.movementService = movementService;
		this.cattleService = cattleService;
		this.cattleRepository = cattleRepository;
	}

	@Override
	public List<TreatmentHistoryDto> getAllTreatmentHistory() {
		return treatmentHistoryRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public List<TreatmentHistoryDto> getTreatmentHistoriesByEId(Long eId) {
		final List<TreatmentHistory> treatmentHistories = treatmentHistoryRepository.findByCattleId(eId);
		return mapToDto(treatmentHistories);
	}

	@Override
	@Transactional
	public Map<String, Object> saveTreatmentHistory(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		final List<TreatmentHistory> treatmentHistories = treatmentHistoryMetadata.getTreatmentHistories();

		Cattle cattle = cattleRepository.findByCattleId(treatmentHistoryMetadata.getCattleId())
                .orElseThrow(() -> new IllegalArgumentException("Cattle not found"));

		validateInductionVO(treatmentHistories);

		final String formattedDate = getCurrentFormattedDate();

		final Long cattleId = cattle.getCattleId();
		final Long processId = treatmentHistoryMetadata.getProcessId();
		final Long userId = treatmentHistoryMetadata.getUserId();

		final List<Comment> commentList = new ArrayList<>();
		final Long commentId = createTreatmentComment(treatmentHistoryMetadata.getComment(), cattleId, processId, null,
				formattedDate, commentList, userId);

		updateTreatmentHistories(treatmentHistories, formattedDate, cattleId, processId, commentId, commentList,
				userId);

		final List<TreatmentHistory> savedTreatmentHistory = treatmentHistoryRepository.saveAll(treatmentHistories);

		final Map<String, Object> outputMap = new HashMap<>();

		final List<TreatmentHistoryDto> savedTreatmentHistoryDtos = mapToDto(savedTreatmentHistory);
		outputMap.put("treatmentHistories", savedTreatmentHistoryDtos);

		if (CollectionUtils.isNotEmpty(commentList)) {
			commentService.saveComments(commentList);
			outputMap.put("comments", commentList);
		}

		final WeightHistoryDto weightHistoryDto = saveWeightHistory(cattleId, formattedDate,
					treatmentHistoryMetadata.getWeight(), userId);
			outputMap.put("weightHistories", weightHistoryDto);

		if(treatmentHistoryMetadata.getPen() != null) {
			final MovementDto movementDto = saveMovement(cattleId, treatmentHistoryMetadata.getPen(), formattedDate,
					userId);
			outputMap.put("movements", movementDto);
		}

		// Update cattle details
		cattle.setNewTagReq(treatmentHistoryMetadata.getNewTagReq());
		cattle.setConditionScore(treatmentHistoryMetadata.getConditionScore());
		cattle = cattleRepository.save(cattle);

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
		final List<TreatmentHistoryResponseDto> treatmentHistoryDtoList = treatmentHistoryRepository
				.findTreatmentHistoryByCattleId(cattleDto.getCattleId());

		treatmentHistoryDtoList.forEach(dto -> {
			if (dto.getTreatmentDate() != null) {
				dto.setTreatmentDate(DateUtils.formatToReadableDate(dto.getTreatmentDate()));
			}
			if (dto.getWithdrawalDate() != null) {
				dto.setWithdrawalDate(DateUtils.formatToReadableDate(dto.getWithdrawalDate()));
			}
		});

		outputMap.put("avgConditionScore", averageConditionScore);
		outputMap.put("treatmentHistory", treatmentHistoryDtoList);
		return outputMap;
	}

	private String getCurrentFormattedDate() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	private void updateTreatmentHistories(final List<TreatmentHistory> treatmentHistories, final String formattedDate,
			final Long cattleId, final Long processId, final Long commentId, final List<Comment> commentList,
			final Long userId) {
		for (final TreatmentHistory treatmentHistory : treatmentHistories) {
			final Long id = treatmentHistoryRepository.getNextSequenceValue();
			treatmentHistory.setTreatmentHistoryId(id);
			treatmentHistory.setTreatmentDate(formattedDate);
			treatmentHistory.setCattleId(cattleId);
			treatmentHistory.setWithdrawalDate(this.getWithdrawalDate(treatmentHistory.getMedicationId()));
			treatmentHistory.setProcessId(processId);
			treatmentHistory.setCommentId(commentId);
			treatmentHistory.setUserId(userId);

			final Long conditionCommentId = createTreatmentComment(treatmentHistory.getConditionComment(), cattleId,
					processId, id, formattedDate, commentList, userId);
			treatmentHistory.setConditionCommentId(conditionCommentId);
		}
	}

	private String getWithdrawalDate(final Long medicationId) {
		Optional<Integer> withdrawalPeriod = medicationRepository.findWithdrawalPeriodByMedicationId(medicationId);
		String formattedDate = withdrawalPeriod
				.map(days -> {
					LocalDate targetDate = LocalDate.now().plusDays(days);
					LocalDateTime dateTime = LocalDateTime.of(targetDate, LocalTime.MIDNIGHT);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					return dateTime.format(formatter);
				})
				.orElse(null);
		return formattedDate;
	}

	private Long createTreatmentComment(final String comment, final Long cattleId, final Long processId,
			final Long entityId, final String formattedDate, final List<Comment> commentList, Long userId) {
		if (StringUtils.isEmpty(comment)) {
			return null;
		}
		final Long commentId = commentService.getNextSequenceValue();

		final Comment treatmentComment = new Comment(commentId, comment, processId, cattleId, userId, entityId,
				formattedDate);

		commentList.add(treatmentComment);
		return commentId;
	}

	private WeightHistoryDto saveWeightHistory(final Long cattleId, final String formattedDate, final Double weight,
			final Long userId) {
		final WeightHistory weightHistory = new WeightHistory();
		weightHistory.setCattleId(cattleId);
		weightHistory.setWeightDateTime(formattedDate);
		weightHistory.setWeight(weight);
		weightHistory.setUserId(userId);

		return weightHistoryService.saveWeightHistory(weightHistory);
	}

	private MovementDto saveMovement(final Long cattleId, final Integer penId, final String formattedDate,
			final Long userId) {
		final Movement movement = new Movement();
		movement.setCattleId(cattleId);
		movement.setPenId(penId);
		movement.setMovementDate(formattedDate);
		movement.setUserId(userId);

		return movementService.saveMovement(movement);
	}

	private void validateInductionVO(List<TreatmentHistory> treatmentHistories) {
		if (CollectionUtils.isEmpty(treatmentHistories)) {
			throw new IllegalArgumentException("Treatment cannot be null or empty.");
		}
		for (final TreatmentHistory treatmentHistory : treatmentHistories) {
			if (treatmentHistory.getMedicalConditionId() == null) {
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
		TreatmentHistoryDto treatmentHistoryDto = new TreatmentHistoryDto();
		treatmentHistoryDto.setTreatmentHistoryId(treatmentHistory.getTreatmentHistoryId());
		treatmentHistoryDto.setCattleId(treatmentHistory.getCattleId());
		treatmentHistoryDto.setUserId(treatmentHistory.getUserId());
		treatmentHistoryDto.setMedicalConditionId(treatmentHistory.getMedicalConditionId());
		treatmentHistoryDto.setMedicationId(treatmentHistory.getMedicationId());
		treatmentHistoryDto.setBatchNumber(treatmentHistory.getBatchNumber());
		treatmentHistoryDto.setTreatmentDate(treatmentHistory.getTreatmentDate());
		treatmentHistoryDto.setCommentId(treatmentHistory.getCommentId());
		treatmentHistoryDto.setConditionCommentId(treatmentHistory.getConditionCommentId());
		treatmentHistoryDto.setQuantity(treatmentHistory.getQuantity());
		return treatmentHistoryDto;
	}

}
