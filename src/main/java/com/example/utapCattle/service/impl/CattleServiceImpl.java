package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.TreatmentHistory;
import com.example.utapCattle.service.*;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.TreatmentHistoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CattleServiceImpl implements CattleService {

    @Autowired
    private CattleRepository cattleRepository;
    @Autowired
    private BreedService breedService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FarmService farmService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private TreatmentHistoryRepository treatmentHistoryRepository;

    @Override
    public List<CattleDto> getAllCattle() { // Return List<CattleDto>
        return cattleRepository.findAll().stream().map(this::mapToDto) // Map each Cattle to CattleDto
                .collect(Collectors.toList());
    }

    @Override
    public CattleDto getCattleById(final Long id) { // Return CattleDto
        final Optional<Cattle> cattle = cattleRepository.findById(id);
        return cattle.map(this::mapToDto).orElse(null); // Map to DTO if found
    }

    @Override
    public CattleDto getCattleByEarTag(String earTag) {
        try {
            final Optional<Cattle> cattle = cattleRepository.findByEarTag(earTag);
            return cattle.map(this::mapToDto).orElse(null);
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public CattleDto saveCattle(final Cattle cattle) throws Exception { // Return CattleDto
        validateCattleInformation(cattle);
        final long nextInductionId = cattleRepository.getNextSequenceValue();
        cattle.setId(nextInductionId);
        final Cattle savedCattle = cattleRepository.save(cattle);
        return mapToDto(savedCattle); // Map the saved Cattle to DTO
    }

    @Override
    public List<String> findEarTagsWithIncompleteInduction() {
        return cattleRepository.findEarTagsWithIncompleteInduction();
    }

    private CattleDto mapToDto(final Cattle cattle) {
        String cattleName = getCattleMarketName(cattle.getSourceMarketId());
        String breedAbbr = getBreedAbbr(cattle.getBreedId());
        String categoryDesc = getCategoryDesc(cattle.getCategoryId());
        String farmName = getFarmName(cattle.getFarmId());
        String agentName = getAgentName(cattle.getAgentId());
        Long treatmentCount = getCattleTotalTreatmentCount(cattle.getCattleId());
        String lastTreatment = getLastWithdrawalDate(cattle.getCattleId());

        return new CattleDto(
                cattle.getId(),
                cattle.getCattleId(),
                cattle.getPrefix(),
                cattle.getEarTag(),
                cattle.getDateOfBirth(),
                cattle.getMotherEarTag(),
                cattle.getBreedId(),
                cattle.getCategoryId(),
                breedAbbr,
                categoryDesc,
                cattle.getFarmId(),
                farmName,
                cattle.getSourceMarketId(),
                cattleName,
                cattle.getDatePurchased(),
                cattle.getPurchasePrice(),
                cattle.getSaleId(),
                cattle.getSalePrice(),
                cattle.getComments(),
                cattle.getVersion(),
                cattle.getPreviousHolding(),
                cattle.getFlatteningFor(),
                cattle.getAgentId(),
                agentName,
                cattle.getConditionScore(),
                cattle.getHealthScore(),
                cattle.getWeightAtSale(),
                cattle.getBodyWeight(),
                cattle.getExpenses(),
                cattle.getSireEarTag(),
                cattle.getSireName(),
                cattle.getPoundPerKgGain(),
                cattle.getHdDayFeeders(),
                cattle.getTagOrdered(),
                cattle.getTagHere(),
                cattle.getCoopOpening(),
                cattle.getCoopClosing(),
                cattle.getResidencies(),
                cattle.getNewtagreq(),
                cattle.getTagId(),
                cattle.getNewTagReqd(),
                cattle.getCattleGroupId(),
                cattle.getConformationId(),
                cattle.getFatCoverId(),
                cattle.getWeightAtPurchase(),
                cattle.getNumPrevMovements(),
                treatmentCount,
                lastTreatment,
                cattle.getIsInductionCompleted()
        );
    }

    public String getLastWithdrawalDate(Long cattleId) {
        return (cattleId != null)
                ? treatmentHistoryRepository.findLatestWithdrawalDateByCattleId(cattleId)
                .map(Date::toString)
                .orElse(null)
                : null;
    }



    private Long getCattleTotalTreatmentCount(Long cattleId) {
        return (cattleId != null) ? treatmentHistoryRepository.countByCattleId(cattleId) : null;
    }

    private String getCattleMarketName(Integer sourceMarketId) {
        return (sourceMarketId != null)
                ? marketService.getMarketById(sourceMarketId.longValue()).getMarketName()
                : null;
    }

    private String getBreedAbbr(Integer breedId) {
        return (breedId != null)
                ? breedService.getBreedById(breedId.longValue()).getBreedabbr()
                : "";
    }

    private String getCategoryDesc(Integer categoryId) {
        return (categoryId != null)
                ? categoryService.getCategoryById(categoryId.longValue()).getCategoryDesc()
                : "";
    }

    private String getFarmName(Integer farmId) {
        return (farmId != null)
                ? farmService.getFarmById(farmId.longValue()).getFarmName()
                : "";
    }

    private String getAgentName(Integer agentId) {
        return (agentId != null)
                ? agentService.getAgentById(agentId.longValue()).getAgentName()
                : "";
    }

    private void validateCattleInformation(final Cattle cattle) throws Exception {
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
    }

}
