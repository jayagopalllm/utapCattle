package com.example.utapCattle.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.service.TbTestHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.TbTestHistoryRepository;

@Service
public class TbTestHistoryServiceImpl implements TbTestHistoryService {

	@Autowired
	private TbTestHistoryRepository tbTestHistoryRepository;

	@Autowired
	private CattleRepository cattleRepository;

	@Override
	public TbTestHistory saveTbTestHistory(final TbTestHistory tbTestHistory) throws Exception {
		validateCattle(tbTestHistory.getEarTag());
		final Long id = tbTestHistoryRepository.getNextSequenceValue();
		tbTestHistory.setTbTestHistoryId(id);
		tbTestHistory.setTestDate(getCurrentDateTime());
		return tbTestHistoryRepository.save(tbTestHistory);
	}

	@Override
	public Long getNextSequenceValue() {
		return tbTestHistoryRepository.getNextSequenceValue();
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

	/**
	 * Returns the current date formatted as a string in the format "yyyy-MM-dd".
	 *
	 * @return The formatted current date.
	 */
	private static LocalDateTime getCurrentDateTime() {
		return LocalDateTime.now();
	}

}
