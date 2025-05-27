package com.example.utapCattle.service;

import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;

import java.util.Map;

public interface TbTestHistoryService {


	public Map<String, Object> getCattleDetailsAndTBTest(final String earTagOrEId) throws Exception;


	Long getNextSequenceValue();

	TbTestHistory saveTBTestData(TreatmentHistoryMetadata treatmentHistoryMetadata);
}
