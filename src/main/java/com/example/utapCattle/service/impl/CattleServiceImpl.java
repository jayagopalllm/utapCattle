package com.example.utapCattle.service.impl;

import com.example.utapCattle.exception.CattleException;
import com.example.utapCattle.mapper.CattleMapper;
import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.service.CattleService;
import com.example.utapCattle.service.repository.CattleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CattleServiceImpl implements CattleService {

	private final CattleRepository cattleRepository;
	private final CattleMapper mapper;

	public CattleServiceImpl(CattleRepository cattleRepository,
							 CattleMapper mapper) {
		this.cattleRepository = cattleRepository;
		this.mapper = mapper;
	}

	@Override
	public List<CattleDto> getAllCattle() {
		return cattleRepository.findAll().stream().map(mapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public CattleDto getCattleById(final Long id) {
		final Optional<Cattle> cattle = cattleRepository.findById(id);
		return cattle.map(mapper::toDto).orElse(null);
	}

	@Override
	public CattleDto getCattleByEarTag(String earTag) {
		final Optional<Cattle> cattle = cattleRepository.findByEarTag(earTag);
		return cattle.map(mapper::toDto).orElse(null);
	}

	@Override
	public CattleDto saveCattle(final Cattle cattle) throws CattleException {
		validateCattleInformation(cattle);
		//final long nextInductionId = cattleRepository.getNextSequenceValue();
		//cattle.setId(nextInductionId);
		final Cattle savedCattle = cattleRepository.save(cattle);
		return mapper.toDto(savedCattle);
	}

	@Override
	public List<String> findEarTagsWithIncompleteInduction() {
		final List<String> earTagList = cattleRepository.findEarTagsWithIncompleteInduction();
		return earTagList;
	}

	private boolean validateCattleInformation(final Cattle cattle) throws CattleException {

		List<String> validationErrors = new ArrayList<>();

		if (StringUtils.isEmpty(cattle.getEarTag())) {
			validationErrors.add("CATTLE_EAR_TAG_MISSING");
		}
		if (StringUtils.isEmpty(cattle.getDateOfBirth())) {
			validationErrors.add("CATTLE_DOB_MISSING");
		}
		if (cattle.getBreedId() == null) {
			validationErrors.add("CATTLE_BREED_ID_MISSING");
		}
		if (cattle.getCategoryId() == null) {
			validationErrors.add("CATTLE_CATEGORY_MISSING");
		}
		if (StringUtils.isEmpty(cattle.getVersion())) {
			validationErrors.add("CATTLE_VERSION_MISSING");
		}
		if (!validationErrors.isEmpty()) {
			throw new CattleException(validationErrors);
		}
		return true;
	}

}
