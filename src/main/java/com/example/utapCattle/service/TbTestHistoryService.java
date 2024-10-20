package com.example.utapCattle.service;

import com.example.utapCattle.model.dto.TbTestHistoryDto;
import com.example.utapCattle.model.entity.TbTestHistory;

public interface TbTestHistoryService {

	TbTestHistoryDto saveTbTestHistory(final TbTestHistoryDto tbTestHistoryDto) throws Exception;

	Long getNextSequenceValue();
}
