package com.example.utapCattle.service;

import java.util.Map;

import com.example.utapCattle.exception.CattleException;
import com.example.utapCattle.exception.CommentException;
import com.example.utapCattle.exception.InductionException;
import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.model.entity.TreatmentHistory;
import com.example.utapCattle.service.impl.TreatmentHistoryServiceImpl;

public interface InductionService {

	public Map<String, Object> saveInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata) throws CommentException, InductionException, CattleException;

	public CattleDto updateCattleDetails(final TreatmentHistoryMetadata treatmentHistoryMetadata) throws InductionException, CattleException;
}
