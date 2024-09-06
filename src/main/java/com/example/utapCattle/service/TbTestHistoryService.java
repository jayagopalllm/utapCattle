package com.example.utapCattle.service;

import com.example.utapCattle.model.entity.TbTestHistory;

public interface TbTestHistoryService {

	TbTestHistory saveTbTestHistory(final TbTestHistory tbTestHistory) throws Exception;

	Long getNextSequenceValue();
}
