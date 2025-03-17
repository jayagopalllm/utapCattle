package com.example.utapCattle.service;

import com.example.utapCattle.model.entity.TbTestHistory;

import java.util.Map;

public interface TbTestHistoryService {

	TbTestHistory saveTbTestHistory(final TbTestHistory tbTestHistory) throws Exception;

	public Map<String, Object> getCattleDetailsAndTBTest(final String earTagOrEId) throws Exception;


	Long getNextSequenceValue();
}
