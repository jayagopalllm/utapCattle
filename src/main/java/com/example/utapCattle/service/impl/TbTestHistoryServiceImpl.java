package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.dto.TreatmentHistoryResponseDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.*;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.TbTestHistoryRepository;
import com.example.utapCattle.service.repository.TreatmentHistoryRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TbTestHistoryServiceImpl implements TbTestHistoryService {

	@Autowired
	private TreatmentHistoryService treatmentHistoryService;

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
	@Transactional
	public TbTestHistory saveTBTestData(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		TbTestHistory tbTestHistory = treatmentHistoryMetadata.getTbTestHistory();
		Long userId = treatmentHistoryMetadata.getUserId();

		Cattle cattle = validateCattle(tbTestHistory.getEarTag());

		treatmentHistoryService.saveTreatmentHistory(treatmentHistoryMetadata);

		tbTestHistory=this.saveTBTestHistory(tbTestHistory, userId);

		return tbTestHistory;

	}

	private TbTestHistory saveTBTestHistory(final TbTestHistory tbTestHistory, final Long userId) {
		Optional<TbTestHistory> existingRecord = tbTestHistoryRepository.getLatestTBTest(tbTestHistory.getCattleId());

		if (existingRecord.isPresent()) {
			if(tbTestHistory.getMeasA2() == null || tbTestHistory.getMeasB2() == null) {
				throw new IllegalArgumentException("Measurement values after 72 Hours cannot be null for existing records.");
			}
			// Update existing record
			TbTestHistory updatedRecord = existingRecord.get();
			updatedRecord.setTestDate(getCurrentDateTime());
			// updatedRecord.setMeasA1(tbTestHistory.getMeasA1());
			// updatedRecord.setMeasB1(tbTestHistory.getMeasB1());
			updatedRecord.setMeasA2(tbTestHistory.getMeasA2());
			updatedRecord.setMeasB2(tbTestHistory.getMeasB2());
			updatedRecord.setReactionDescA(tbTestHistory.getReactionDescA());
			updatedRecord.setReactionDescB(tbTestHistory.getReactionDescB());
			updatedRecord.setOverallResult(tbTestHistory.getOverallResult());
			updatedRecord.setRemarks(tbTestHistory.getRemarks());
			updatedRecord.setUserId2(userId);

			return tbTestHistoryRepository.save(updatedRecord);
		} else {
			// Create new record
			final Long id = tbTestHistoryRepository.getNextSequenceValue();
			tbTestHistory.setTbTestHistoryId(id);
			tbTestHistory.setTestDate(getCurrentDateTime());
			tbTestHistory.setUserId1(userId);
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

	private Cattle validateCattle(final String earTag) {
		try {
			return cattleRepository.findByEarTag(earTag).orElseThrow(() -> new IllegalArgumentException("No Cattle record found with the given EarTag: " + earTag));
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
