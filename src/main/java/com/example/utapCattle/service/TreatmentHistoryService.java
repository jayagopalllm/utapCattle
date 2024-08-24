package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;

public interface TreatmentHistoryService {

	List<TreatmentHistoryDto> getAllTreatmentHistory();

	TreatmentHistoryDto getTreatmentHistoryById(Long id);

	public List<TreatmentHistoryDto> saveTreatmentHistory(final TreatmentHistoryMetadata treatmentHistoryMetadata);
}
