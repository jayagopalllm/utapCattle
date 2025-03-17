package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.dto.TreatmentHistoryResponseDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.service.*;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.TbTestHistoryRepository;
import com.example.utapCattle.service.repository.TreatmentHistoryRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TbTestHistoryServiceImpl implements TbTestHistoryService {

	private final TbTestHistoryRepository tbTestHistoryRepository;
	private final CattleRepository cattleRepository;
	private final TreatmentHistoryRepository treatmentHistoryRepository;
	private final CattleService cattleService;

	public TbTestHistoryServiceImpl(TbTestHistoryRepository tbTestHistoryRepository, CattleRepository cattleRepository, TreatmentHistoryRepository treatmentHistoryRepository, CattleService cattleService) {
		this.tbTestHistoryRepository = tbTestHistoryRepository;
		this.cattleRepository = cattleRepository;
		this.treatmentHistoryRepository = treatmentHistoryRepository;
		this.cattleService = cattleService;
	}

	@Override
	public TbTestHistory saveTbTestHistory(final TbTestHistory tbTestHistory) throws Exception {
		validateCattle(tbTestHistory.getEarTag());

		Optional<TbTestHistory> existingRecord = tbTestHistoryRepository.getLatestTBTest(tbTestHistory.getCattleId());

		if (existingRecord.isPresent()) {
			// Update existing record
			TbTestHistory updatedRecord = existingRecord.get();
			updatedRecord.setTestDate(getCurrentDateTime());
			updatedRecord.setMeasA1(tbTestHistory.getMeasA1());
			updatedRecord.setMeasB1(tbTestHistory.getMeasB1());
			updatedRecord.setMeasA2(tbTestHistory.getMeasA2());
			updatedRecord.setMeasB2(tbTestHistory.getMeasB2());
			updatedRecord.setReactionDescA(tbTestHistory.getReactionDescA());
			updatedRecord.setReactionDescB(tbTestHistory.getReactionDescB());
			updatedRecord.setOverallResult(tbTestHistory.getOverallResult());
			updatedRecord.setRemarks(tbTestHistory.getRemarks());

			return tbTestHistoryRepository.save(updatedRecord);
		} else {
			// Create new record
			final Long id = tbTestHistoryRepository.getNextSequenceValue();
			tbTestHistory.setTbTestHistoryId(id);
			tbTestHistory.setTestDate(getCurrentDateTime());
			return tbTestHistoryRepository.save(tbTestHistory);
		}
	}


	@Override
	public Map<String, Object> getCattleDetailsAndTBTest(String earTagOrEId) throws Exception {

	final CattleDto cattleDto = cattleService.getCattleByEarTag(earTagOrEId);
			if (cattleDto == null) {
				throw new BadRequestException("Cattle not found - " + earTagOrEId);
			}
			if (cattleDto.getCattleId() == null) {
				throw new BadRequestException("Induction not yet completed for cattle - " + earTagOrEId);
			}
			final Map<String, Object> outputMap = new HashMap<>();
			outputMap.put("cattle", cattleDto);
			final List<TreatmentHistoryResponseDto> treatmentHistoryDtoList = treatmentHistoryRepository
					.findTreatmentHistoryByCattleId(cattleDto.getCattleId());
		Optional<TbTestHistory> testHistory = tbTestHistoryRepository.getLatestTBTest(cattleDto.getCattleId());
		outputMap.put("testHistory", testHistory.orElse(null));
			outputMap.put("treatmentHistory", treatmentHistoryDtoList);
			return outputMap;
	}

	@Override
	public Long getNextSequenceValue() {
		return tbTestHistoryRepository.getNextSequenceValue();
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

	private static LocalDateTime getCurrentDateTime() {
		return LocalDateTime.now();
	}

}
