package com.example.utapCattle.service;

import java.util.List;
import java.util.Map;

import com.example.utapCattle.exception.CommentException;
import com.example.utapCattle.exception.TreatmentHistoryException;
import com.example.utapCattle.model.dto.MovementDto;
import com.example.utapCattle.model.dto.TreatmentHistoryDto;
import com.example.utapCattle.model.dto.TreatmentHistoryMetadataDto;
import com.example.utapCattle.model.dto.WeightHistoryDto;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;

public interface TreatmentHistoryService {

	List<TreatmentHistoryDto> getAllTreatmentHistory();

	List<TreatmentHistoryDto> getTreatmentHistoriesByEId(final Long id);

	public Map<String, Object> saveTreatmentHistory(final TreatmentHistoryMetadataDto treatmentHistoryMetadataDto) throws CommentException, TreatmentHistoryException;

	public Map<String, Object> getCattleDetailsAndAverageConditionScore(final String earTagOrEId) throws Exception;
}
