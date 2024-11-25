package com.example.utapCattle.service.impl;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.InductionService;
import com.example.utapCattle.service.TreatmentHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;

@Service
public class InductionServiceImpl implements InductionService {

	@Autowired
	private CattleRepository cattleRepository;

	@Autowired
	private TreatmentHistoryService treatmentHistoryService;

	@Override
	public final Map<String, Object> saveInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata) {

		validateInduction(treatmentHistoryMetadata);

		treatmentHistoryMetadata.setProcessId(new SecureRandom().nextLong());

		updateCattleDetails(treatmentHistoryMetadata);

		return treatmentHistoryService
				.saveTreatmentHistory(treatmentHistoryMetadata);
	}

	private void validateInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		if (treatmentHistoryMetadata.getCattleId() == null) {
			throw new IllegalArgumentException("EId is a mandatory field and cannot be null or empty.");
		}
		if (StringUtils.isBlank(treatmentHistoryMetadata.getEarTag())) {
			throw new IllegalArgumentException("EarTag is a mandatory field and cannot be null or empty.");
		}
	}

	private void updateCattleDetails(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
		final String earTag = treatmentHistoryMetadata.getEarTag();
		final Optional<Cattle> existingCattle = cattleRepository.findByEarTag(earTag);
	
		if (existingCattle.isPresent()) {
			final Cattle cattle = existingCattle.get();
			cattle.setCattleId(Long.valueOf(treatmentHistoryMetadata.getCattleId()));
			cattle.setIsInductionCompleted(true);
			cattleRepository.save(cattle);
		} else {
			throw new IllegalArgumentException("No Cattle record found with the given EarTag: " + earTag);
		}
	}
	

}
