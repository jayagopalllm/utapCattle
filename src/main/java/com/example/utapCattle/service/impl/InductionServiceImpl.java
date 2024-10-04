package com.example.utapCattle.service.impl;

import com.example.utapCattle.exception.CattleException;
import com.example.utapCattle.exception.CommentException;
import com.example.utapCattle.exception.InductionException;
import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.CattleService;
import com.example.utapCattle.service.InductionService;
import com.example.utapCattle.service.TreatmentHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

@Service
public class InductionServiceImpl implements InductionService {

	private final CattleRepository cattleRepository;
	private final CattleService cattleService;
	private final TreatmentHistoryService treatmentHistoryService;

	public InductionServiceImpl(CattleRepository cattleRepository,
								CattleService cattleService,
								TreatmentHistoryService treatmentHistoryService) {
		this.cattleRepository = cattleRepository;
		this.cattleService = cattleService;
		this.treatmentHistoryService = treatmentHistoryService;
	}

	@Override
	public final Map<String, Object> saveInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata) throws CommentException, InductionException, CattleException {

		validateInduction(treatmentHistoryMetadata);

		treatmentHistoryMetadata.setProcessId(new SecureRandom().nextLong());

		updateCattleDetails(treatmentHistoryMetadata);

		return treatmentHistoryService
				.saveTreatmentHistory(treatmentHistoryMetadata);
	}

	private void validateInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata) throws InductionException {
		if (treatmentHistoryMetadata.getCattleId() == null) {
			throw new InductionException("EId is a mandatory field and cannot be null or empty.");
		}
		if (StringUtils.isBlank(treatmentHistoryMetadata.getEarTag())) {
			throw new InductionException("EarTag is a mandatory field and cannot be null or empty.");
		}
	}

	public CattleDto updateCattleDetails(final TreatmentHistoryMetadata treatmentHistoryMetadata) throws InductionException, CattleException {
		final String earTag = treatmentHistoryMetadata.getEarTag();
		final Optional<Cattle> existingCattle = cattleRepository.findByEarTag(earTag);
	
		if (existingCattle.isPresent()) {
			final Cattle cattle = existingCattle.get();
			//TODO: is cattle id mandatory or not ?
			//cattle.setCattleId(Long.valueOf(treatmentHistoryMetadata.getCattleId()));
			cattle.setIsInductionCompleted(true);
			return cattleService.saveCattle(cattle);
		} else {
			throw new InductionException("No Cattle record found with the given EarTag: " + earTag);
		}
	}
	

}
