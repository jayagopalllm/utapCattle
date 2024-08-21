package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.service.CattleService;
import com.example.utapCattle.service.repository.CattleRepository;

@Service
public class CattleServiceImpl implements CattleService {

	@Autowired
	private CattleRepository cattleRepository;

	@Override
	public List<CattleDto> getAllCattle() { // Return List<CattleDto>
		return cattleRepository.findAll().stream().map(this::mapToDto) // Map each Cattle to CattleDto
				.collect(Collectors.toList());
	}

	@Override
	public CattleDto getCattleById(Long id) { // Return CattleDto
		final Optional<Cattle> cattle = cattleRepository.findById(id);
		return cattle.map(this::mapToDto).orElse(null); // Map to DTO if found
	}

	@Override
	public CattleDto saveCattle(Cattle cattle) throws Exception { // Return CattleDto
		validateCattleInformation(cattle);
		final long nextInductionId = cattleRepository.getNextSequenceValue();
		cattle.setId(nextInductionId);
		final Cattle savedCattle = cattleRepository.save(cattle);
		return mapToDto(savedCattle); // Map the saved Cattle to DTO
	}

	@Override
	public List<String> getEartags() {
		final List<String> earTagList = cattleRepository.getEarTagList();
		return earTagList;
	}

	private CattleDto mapToDto(Cattle cattle) {
		return new CattleDto(cattle.getId(), cattle.getCattleId(), cattle.getPrefix(), cattle.getEarTag(),
				cattle.getDateOfBirth(), cattle.getMotherEarTag(), cattle.getBreedId(), cattle.getCategoryId(),
				cattle.getFarmId(), cattle.getSourceMarketId(), cattle.getDatePurchased(), cattle.getPurchasePrice(),
				cattle.getSaleId(), cattle.getSalePrice(), cattle.getComments(), cattle.getVersion(),
				cattle.getPreviousHolding(), cattle.getFlatteningFor(), cattle.getAgentId(), cattle.getConditionScore(),
				cattle.getHealthScore(), cattle.getWeightAtSale(), cattle.getBodyWeight(), cattle.getExpenses(),
				cattle.getSireEarTag(), cattle.getSireName(), cattle.getPoundPerKgGain(), cattle.getHdDayFeeders(),
				cattle.getTagOrdered(), cattle.getTagHere(), cattle.getCoopOpening(), cattle.getCoopClosing(),
				cattle.getResidencies(), cattle.getNewtagreq(), cattle.getTagId(), cattle.getNewTagReqd(),
				cattle.getCattleGroupId(), cattle.getConformationId(), cattle.getFatCoverId(),
				cattle.getWeightAtPurchase(), cattle.getNumPrevMovements());
	}

	private boolean validateCattleInformation(final Cattle cattle) throws Exception {
		if (StringUtils.isEmpty(cattle.getEarTag())) {
			throw new Exception("CATTLE_EAR_TAG_MISSING");
		}
		if (StringUtils.isEmpty(cattle.getDateOfBirth())) {
			throw new Exception("CATTLE_DOB_MISSING");
		}
		if (cattle.getBreedId() == null) {
			throw new Exception("CATTLE_BREED_ID_MISSING");
		}
		if (cattle.getCategoryId() == null) {
			throw new Exception("CATTLE_CATEGORY_MISSING");
		}
		if (StringUtils.isEmpty(cattle.getVersion())) {
			throw new Exception("CATTLE_VERSION_MISSING");
		}
		return true;
	}

}
