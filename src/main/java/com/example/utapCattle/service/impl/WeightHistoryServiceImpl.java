package com.example.utapCattle.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.WeightHistoryDto;
import com.example.utapCattle.model.dto.WeightHistoryInfo;
import com.example.utapCattle.model.dto.WeightHistoryProgressDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.Movement;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.MovementService;
import com.example.utapCattle.service.WeightHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.WeightHistoryRepository;

@Service
public class WeightHistoryServiceImpl implements WeightHistoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeightHistoryServiceImpl.class);

	private final WeightHistoryRepository weightHistoryRepository;

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter DATE_FORMATTER_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	public WeightHistoryServiceImpl(WeightHistoryRepository weightHistoryRepository) {
		this.weightHistoryRepository = weightHistoryRepository;
	}

	@Autowired
	private MovementService movementService;

	@Autowired
	private CattleRepository cattleRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WeightHistoryDto saveWeightHistory(final WeightHistory weightHistory) {
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

	@Override
	public void saveWeightAndMovement(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		validateCattle(treatmentHistoryMetadata.getEarTag());

		final Long cattleId = treatmentHistoryMetadata.getCattleId();
		final String formattedDate = getCurrentFormattedDate();

		// Save Weight History if the Record Weight flag is true
		if (Boolean.TRUE.equals(treatmentHistoryMetadata.getRecordWeight())) {
			final Double weight = treatmentHistoryMetadata.getWeight();
			if (weight == null) {
				throw new IllegalArgumentException("Weight cannot be empty");
			}
			final WeightHistory weightHistory = new WeightHistory();
			weightHistory.setCattleId(cattleId);
			weightHistory.setWeightDateTime(formattedDate);
			weightHistory.setWeight(weight);

			saveWeightHistory(weightHistory);
		}

		// Save Movement if the Record Movement flag is true
		if (Boolean.TRUE.equals(treatmentHistoryMetadata.getRecordMovement())) {
			final Movement movement = new Movement();
			final Integer pen = treatmentHistoryMetadata.getPen();
			if (pen == null) {
				throw new IllegalArgumentException("Pen cannot be empty");
			}
			movement.setCattleId(cattleId);
			movement.setPenId(pen);
			movement.setMovementDate(formattedDate);

			movementService.saveMovement(movement);
		}
	}

	@Override
	public List<WeightHistoryProgressDto> deriveWeightHistoryInfoByCattleId(final Long cattleId) {
		final List<WeightHistory> weightHistories = weightHistoryRepository.findByCattleIdOrderByWeightId(cattleId);
		sortWeightHistoryByDate(weightHistories);
		return deriveWeightHistoryInfo(weightHistories);
	}

	public List<WeightHistoryProgressDto> deriveWeightHistoryInfo(final List<WeightHistory> weightHistories) {
		final List<WeightHistoryProgressDto> weightHistoryInfos = new ArrayList<>();
		WeightHistory previousWeightHistory = null;

		for (final WeightHistory currentWeightHistory : weightHistories) {
			final WeightHistoryProgressDto info = new WeightHistoryProgressDto();

			// Set the current date and weight
			info.setDate(currentWeightHistory.getWeightDateTime());
			info.setWeight(currentWeightHistory.getWeight());

			if (previousWeightHistory != null) {
				// Set the previous weight
				info.setPreviousWeight(previousWeightHistory.getWeight());

				// Calculate the weight difference
				final double weightDiff = currentWeightHistory.getWeight() - previousWeightHistory.getWeight();
				info.setWeightDiff(weightDiff);

				LOGGER.debug("weightDiff = {} ", weightDiff);

				// Calculate the number of days between current and previous dates
				long daysBetween = calculateDaysBetween(previousWeightHistory.getWeightDateTime(),
						currentWeightHistory.getWeightDateTime());

				if (daysBetween <= 0) {
					daysBetween = 1;
				}

				LOGGER.debug("daysBetween = {} ", daysBetween);

				// Calculate DLWG (Daily Live Weight Gain)
				final double dlwg = weightDiff / daysBetween;
				final double roundedDlwg = Math.round(dlwg * 100.0) / 100.0;

				LOGGER.debug("dlwg = {} ", dlwg);

				info.setDlwg(roundedDlwg);
			}

			weightHistoryInfos.add(info);
			previousWeightHistory = currentWeightHistory;
		}

		return weightHistoryInfos;
	}

	@Override
	public List<WeightHistoryInfo> getWeightHistoryByPen(Long penId) {
		// Step 1: Get all cattle IDs associated with the given penId from the Movement
		// table
		final List<Long> cattleIds = movementService.findCattleIdsByPenId(penId);

		// Step 2: For each cattle ID, retrieve weight history and process it
		return cattleIds.stream().map(this::processWeightHistory).collect(Collectors.toList());
	}

	private WeightHistoryInfo processWeightHistory(final Long cattleId) {
		// Retrieve the weight history for the given cattle ID, ordered by weight date
		final List<WeightHistory> weightHistories = weightHistoryRepository.findByCattleIdOrderByWeightId(cattleId);

		if (weightHistories.isEmpty()) {
			throw new IllegalArgumentException("No weight history found for the given cattle ID.");
		}

		sortWeightHistoryByDate(weightHistories);

		// Step 3: Calculate Last Weight and Last DLWG
		final WeightHistory lastWeightHistory = weightHistories.get(weightHistories.size() - 1);
		final WeightHistory previousWeightHistory = weightHistories.size() > 1
				? weightHistories.get(weightHistories.size() - 2)
				: null;

		Double lastWeight = lastWeightHistory.getWeight().doubleValue();
		Double lastDLWG = 0.0;

		if (previousWeightHistory != null) {
			// Calculate the number of days between current and previous dates
			final long daysBetween = calculateDaysBetween(previousWeightHistory.getWeightDateTime(),
					lastWeightHistory.getWeightDateTime());

			if (daysBetween > 0) {
				// Calculate weight difference using Double
				final double weightDifference = lastWeightHistory.getWeight() - previousWeightHistory.getWeight();

				// Calculate DLWG using Double
				lastDLWG = weightDifference / daysBetween;
			}
		}

		// Step 4: Calculate Overall DLWG
		Double overallDLWG = 0.0;
		if (weightHistories.size() > 1) {
			final WeightHistory firstWeightHistory = weightHistories.get(0);
			// Calculate the number of days between current and previous dates
			final long totalDays = calculateDaysBetween(firstWeightHistory.getWeightDateTime(),
					lastWeightHistory.getWeightDateTime());

			LOGGER.debug("totalDays = {}", totalDays);

			if (totalDays > 0) {
				// Calculate total weight difference using Double
				final double totalWeightDifference = lastWeightHistory.getWeight() - firstWeightHistory.getWeight();
				LOGGER.debug("totalWeightDifference = {}", totalWeightDifference);
				// Calculate overall DLWG using Double
				overallDLWG = totalWeightDifference / totalDays;
			}
		}

		// Step 5: Create WeightHistoryInfo object and return
		final WeightHistoryInfo weightHistoryInfo = new WeightHistoryInfo();
		weightHistoryInfo.setEid(cattleId);
		weightHistoryInfo.setLastWeight(lastWeight);
		weightHistoryInfo.setLastDLWG(lastDLWG);
		weightHistoryInfo.setOverallDLWG(overallDLWG);

		return weightHistoryInfo;
	}

	// Find the Cattle record by earTag
	private void validateCattle(final String earTag) {
		try {
			final Optional<Cattle> existingCattle = cattleRepository.findByEarTag(earTag);
			if (existingCattle.isEmpty()) {
				// Handle case where Cattle with the given earTag does not exist
				throw new IllegalArgumentException("No Cattle record found with the given EarTag: " + earTag);
			}
		} catch (final NumberFormatException e) {
			throw new IllegalArgumentException("No Cattle record found with the given EarTag: " + earTag);
		} catch (final Exception e) {
			throw e;
		}

	}

	private long calculateDaysBetween(final String previousDateStr, final String currentDateStr) {
		// Convert the date strings to LocalDate
		final LocalDate previousDate = parseDate(previousDateStr);
		final LocalDate currentDate = parseDate(currentDateStr);

		// Calculate the days between the two dates
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
				final LocalDate date1 = parseDate(wh1.getWeightDateTime());
				final LocalDate date2 = parseDate(wh2.getWeightDateTime());
				return date1.compareTo(date2);
			}
		});
	}

	private LocalDate parseDate(String dateStr) {
		try {
			final DateTimeFormatter dateTimeFormatter = dateStr.indexOf('-') >= 0 ? DATE_FORMATTER_1 : DATE_FORMATTER;
			return LocalDate.parse(dateStr, dateTimeFormatter);
		} catch (final Exception e) {
			throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
		}
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

}
