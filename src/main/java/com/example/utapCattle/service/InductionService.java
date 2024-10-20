package com.example.utapCattle.service;

import java.util.Map;

import com.example.utapCattle.exception.CattleException;
import com.example.utapCattle.exception.CommentException;
import com.example.utapCattle.exception.InductionException;
import com.example.utapCattle.exception.TreatmentHistoryException;
import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.dto.TreatmentHistoryMetadataDto;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.model.entity.TreatmentHistory;
import com.example.utapCattle.service.impl.TreatmentHistoryServiceImpl;

public interface InductionService {

	public Map<String, Object> saveInduction(final TreatmentHistoryMetadataDto treatmentHistoryMetadataDto) throws CommentException, InductionException, CattleException, TreatmentHistoryException;

	public CattleDto updateCattleDetails(final TreatmentHistoryMetadataDto treatmentHistoryMetadataDto) throws InductionException, CattleException;
}
