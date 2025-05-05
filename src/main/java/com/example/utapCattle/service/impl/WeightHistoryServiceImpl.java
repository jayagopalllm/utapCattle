package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.*;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.Movement;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.MovementService;
import com.example.utapCattle.service.WeightHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.WeightHistoryRepository;
import com.example.utapCattle.utils.DateUtils;

import jakarta.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeightHistoryServiceImpl implements WeightHistoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeightHistoryServiceImpl.class);

	private final WeightHistoryRepository weightHistoryRepository;
	private final MovementService movementService;
	private final CattleRepository cattleRepository;

	public WeightHistoryServiceImpl(WeightHistoryRepository weightHistoryRepository,
									MovementService movementService,
									CattleRepository cattleRepository) {
		this.weightHistoryRepository = weightHistoryRepository;
		this.movementService = movementService;
		this.cattleRepository = cattleRepository;
	}

	public WeightHistoryDto saveWeightHistory(final WeightHistory weightHistory) {
		weightHistory.setWeightHistoryId(getNextSequenceValue());
		final WeightHistory savedWeightHistory = weightHistoryRepository.save(weightHistory);

		return mapToDto(savedWeightHistory);
	}

	public Long getNextSequenceValue() {
		return weightHistoryRepository.getNextSequenceValue();
	}

	@Override
	public void saveWeightAndMovement(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		validateCattle(treatmentHistoryMetadata.getEarTag());

		final Long cattleId = treatmentHistoryMetadata.getCattleId();
		final Long userId= treatmentHistoryMetadata.getUserId();
		final String formattedDate = getCurrentFormattedDate();

		if (Boolean.TRUE.equals(treatmentHistoryMetadata.getRecordWeight())) {
			final Double weight = treatmentHistoryMetadata.getWeight();
			if (weight == null) {
				throw new IllegalArgumentException("Weight cannot be empty");
			}
			final WeightHistory weightHistory = new WeightHistory();
			weightHistory.setCattleId(cattleId);
			weightHistory.setWeightDateTime(formattedDate);
			weightHistory.setWeight(weight);
			weightHistory.setUserId(userId);

			saveWeightHistory(weightHistory);
		}

		if (Boolean.TRUE.equals(treatmentHistoryMetadata.getRecordMovement())) {
			final Movement movement = new Movement();
			final Integer pen = treatmentHistoryMetadata.getPen();
			if (pen == null) {
				throw new IllegalArgumentException("Pen cannot be empty");
			}
			movement.setCattleId(cattleId);
			movement.setPenId(pen);
			movement.setMovementDate(formattedDate);
			movement.setUserId(userId);

			movementService.saveMovement(movement);
		}
	}

	@Override
	public List<WeightHistoryProgressDto> deriveWeightHistoryInfoByCattleId(final String cattleId) {
		final Optional<Cattle> existingCattle = cattleRepository.findByEarTag(cattleId);
		Cattle cattle = null;
		if (existingCattle.isPresent()) {
			cattle = existingCattle.get();
		}
		assert cattle != null;
		final List<WeightHistory> weightHistories = weightHistoryRepository.findByCattleIdOrderByWeightId(cattle.getCattleId());
		//Sort the weight history in the ascending order of date for the calcualtion of prev weight, dlwg etc
		sortWeightHistoryByDate(weightHistories);
		return deriveWeightHistoryInfo(weightHistories);
	}

	public List<WeightHistoryProgressDto> deriveWeightHistoryInfo(final List<WeightHistory> weightHistories) {
		final List<WeightHistoryProgressDto> weightHistoryInfos = new ArrayList<>();
		WeightHistory previousWeightHistory = null;

		for (final WeightHistory currentWeightHistory : weightHistories) {
			final WeightHistoryProgressDto info = new WeightHistoryProgressDto();

			info.setDate(DateUtils.formatToReadableDate(currentWeightHistory.getWeightDateTime()));
			info.setWeight(currentWeightHistory.getWeight());

			if (previousWeightHistory != null) {
				info.setPreviousWeight(previousWeightHistory.getWeight());

				final double weightDiff = currentWeightHistory.getWeight() - previousWeightHistory.getWeight();
				info.setWeightDiff(Math.round(weightDiff * 100.0) / 100.0); // Rounds to 2 decimal places



				long daysBetween = calculateDaysBetween(previousWeightHistory.getWeightDateTime(),
						currentWeightHistory.getWeightDateTime());

				if (daysBetween <= 0) {
					daysBetween = 1;
				}


				final double dlwg = weightDiff / daysBetween;
				final double roundedDlwg = Math.round(dlwg * 100.0) / 100.0;


				info.setDlwg(roundedDlwg);
			}

			weightHistoryInfos.add(info);
			previousWeightHistory = currentWeightHistory;
		}
		//Arrange the weight history in the descending order of date for displaying in the
		Collections.reverse(weightHistoryInfos);
		return weightHistoryInfos;
	}
	@Override
	public WeightHistory getLatestWeightHistory(Long cattleId) {
		final WeightHistory weightHistory = weightHistoryRepository.findLatestWeightByCattleId(cattleId);
		return weightHistory;
	}


	@Override
	public List<WeightHistoryInfo> getWeightHistoryByPen(Long penId) {
		final List<Long> cattleIds = movementService.findCattleIdsByPenId(penId);
		return cattleIds.stream().map(this::processWeightHistory).collect(Collectors.toList());
	}

	public List<WeightHistDto> getWeightHistoryForToday(LocalDate date1, Long userFarmId) {
		// Ensure date is in 'YYYY-MM-DD' format
		String date = date1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		List<Tuple> weightHistories = weightHistoryRepository.findWeightHistoryWithDetails(date,userFarmId);
		List<WeightHistDto> weightHistDtos = new ArrayList<>();


		for (Tuple hist : weightHistories) {
			WeightHistDto dto =new WeightHistDto();
			dto.setEarTag(hist.get("eartag", String.class));
			dto.setCattleId(hist.get("cattleid", Long.class));
			dto.setWeight(hist.get("weight", Double.class));
			dto.setWeightDateTime(DateUtils.formatToReadableDate(hist.get("weightdatetime", String.class)));
			Double dlwgFarm= hist.get("dlwgfarm", Double.class);
			dlwgFarm=(dlwgFarm!=null) ? Double.parseDouble(String.format("%.2f", dlwgFarm)) : 0.0;
			dto.setDlwgFarm(dlwgFarm);
			weightHistDtos.add(dto);

		}

		return weightHistDtos;
	}


	private WeightHistoryInfo processWeightHistory(final Long cattleId) {
		final List<WeightHistory> weightHistories = weightHistoryRepository.findByCattleIdOrderByWeightId(cattleId);

		if (weightHistories.isEmpty()) {
			throw new IllegalArgumentException("No weight history found for the given cattle ID.");
		}

		sortWeightHistoryByDate(weightHistories);

		final WeightHistory lastWeightHistory = weightHistories.get(weightHistories.size() - 1);
		final WeightHistory previousWeightHistory = weightHistories.size() > 1
				? weightHistories.get(weightHistories.size() - 2)
				: null;

		Double lastWeight = lastWeightHistory.getWeight().doubleValue();
		Double lastDLWG = 0.0;

		if (previousWeightHistory != null) {
			final long daysBetween = calculateDaysBetween(previousWeightHistory.getWeightDateTime(),
					lastWeightHistory.getWeightDateTime());

			if (daysBetween > 0) {
				final double weightDifference = lastWeightHistory.getWeight() - previousWeightHistory.getWeight();

				lastDLWG = weightDifference / daysBetween;
			}
		}

		Double overallDLWG = 0.0;
		if (weightHistories.size() > 1) {
			final WeightHistory firstWeightHistory = weightHistories.get(0);
			final long totalDays = calculateDaysBetween(firstWeightHistory.getWeightDateTime(),
					lastWeightHistory.getWeightDateTime());


			if (totalDays > 0) {
				final double totalWeightDifference = lastWeightHistory.getWeight() - firstWeightHistory.getWeight();
				overallDLWG = totalWeightDifference / totalDays;
			}
		}

		final WeightHistoryInfo weightHistoryInfo = new WeightHistoryInfo();
		weightHistoryInfo.setEid(cattleId);
		weightHistoryInfo.setLastWeight(lastWeight);
		weightHistoryInfo.setLastDLWG(lastDLWG);
		weightHistoryInfo.setOverallDLWG(overallDLWG);

		return weightHistoryInfo;
	}


	private void validateCattle(final String earTag) {
		try {
			final Optional<Cattle> existingCattle = cattleRepository.findByEarTag(earTag);
			if (existingCattle.isEmpty()) {
				throw new IllegalArgumentException("No Cattle record found with the given EarTag: " + earTag);
			}
		} catch (final NumberFormatException e) {
			throw new IllegalArgumentException("No Cattle record found with the given EarTag: " + earTag);
		} catch (final Exception e) {
			throw e;
		}
	}

	private long calculateDaysBetween(final String previousDateStr, final String currentDateStr) {
		final LocalDate previousDate = DateUtils.parseToDate(previousDateStr);
		final LocalDate currentDate = DateUtils.parseToDate(currentDateStr);

		return ChronoUnit.DAYS.between(previousDate, currentDate);
	}

	private WeightHistoryDto mapToDto(final WeightHistory weightHistory) {
		return new WeightHistoryDto(weightHistory.getWeightHistoryId(), weightHistory.getCattleId(),
				weightHistory.getWeightDateTime(), weightHistory.getWeight(), weightHistory.getUserId());
	}

	private void sortWeightHistoryByDate(List<WeightHistory> weightHistories) {
		Collections.sort(weightHistories, new Comparator<WeightHistory>() {
			@Override
			public int compare(WeightHistory wh1, WeightHistory wh2) {
				final LocalDateTime  date1 = DateUtils.parseToDateTime(wh1.getWeightDateTime());
				final LocalDateTime  date2 = DateUtils.parseToDateTime(wh2.getWeightDateTime());
				return date1.compareTo(date2);
			}
		});
	}

	private String getCurrentFormattedDate() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

	}

}
