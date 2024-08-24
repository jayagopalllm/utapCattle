package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;

public interface InductionService {

	public List<TreatmentHistoryDto> saveInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata);
}
