package com.example.utapCattle.service.impl;

import com.example.utapCattle.exception.CattleException;
import com.example.utapCattle.exception.TbTestHistoryException;
import com.example.utapCattle.mapper.TbTestHistoryMapper;
import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.dto.TbTestHistoryDto;
import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.service.CattleService;
import com.example.utapCattle.service.TbTestHistoryService;
import com.example.utapCattle.service.repository.TbTestHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TbTestHistoryServiceImpl implements TbTestHistoryService {

	private final TbTestHistoryRepository tbTestHistoryRepository;
	private final CattleService cattleService;
	private final TbTestHistoryMapper mapper;

	public TbTestHistoryServiceImpl(TbTestHistoryRepository tbTestHistoryRepository,
									CattleService cattleService,
									TbTestHistoryMapper mapper) {
		this.tbTestHistoryRepository = tbTestHistoryRepository;
		this.cattleService = cattleService;
		this.mapper = mapper;
	}

	@Override
	public TbTestHistoryDto saveTbTestHistory(final TbTestHistoryDto tbTestHistoryDto) throws Exception {
		validateCattle(tbTestHistoryDto.getEarTag());
		final Long id = tbTestHistoryRepository.getNextSequenceValue();
		return mapper.toDto(tbTestHistoryRepository.save(mapper.toEntity(tbTestHistoryDto.builder().tbTestHistoryId(id).testDate(LocalDateTime.now()).build())));
	}

	@Override
	public Long getNextSequenceValue() {
		return tbTestHistoryRepository.getNextSequenceValue();
	}

	private void validateCattle(final String earTag) throws TbTestHistoryException {
		final CattleDto existingCattle = cattleService.getCattleByEarTag(earTag);
		if (existingCattle == null ) {
			throw new TbTestHistoryException("No Cattle record found with the given EarTag: " + earTag);
		}
	}
}
