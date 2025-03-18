package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.*;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.TreatmentHistoryRepository;
import com.example.utapCattle.service.repository.WeightHistoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CattleServiceImpl implements CattleService {

    private final CattleRepository cattleRepository;
    private final BreedService breedService;
    private final CategoryService categoryService;
    private final FarmService farmService;
    private final MarketService marketService;
    private final CustomerService customerService;
    private final AgentService agentService;
    private final TreatmentHistoryRepository treatmentHistoryRepository;
    private final WeightHistoryService weightHistoryService;

    public CattleServiceImpl(CattleRepository cattleRepository
            , BreedService breedService
            , CategoryService categoryService
            , FarmService farmService
            , MarketService marketService
            , CustomerService customerService
            , AgentService agentService
            , TreatmentHistoryRepository treatmentHistoryRepository
            , WeightHistoryService weightHistoryService) {
        this.cattleRepository = cattleRepository;
        this.breedService = breedService;
        this.categoryService = categoryService;
        this.farmService = farmService;
        this.marketService = marketService;
        this.customerService = customerService;
        this.agentService = agentService;
        this.treatmentHistoryRepository = treatmentHistoryRepository;
        this.weightHistoryService = weightHistoryService;
    }

    @Override
    public List<CattleDto> getAllCattle() { // Return List<CattleDto>
        return cattleRepository.findAll().stream().map(this::mapToDto) // Map each Cattle to CattleDto
                .collect(Collectors.toList());
    }

    @Override
    public CattleDto getCattleById(final Long id) { // Return CattleDto
        final Optional<Cattle> cattle = cattleRepository.findByCattleId(id);
        return cattle.map(this::mapToDto).orElse(null);
        // Map to DTO if found
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
    public CattleDto updateCattle(final Long id, final Cattle cattle) throws Exception { // Return CattleDto
//        validateCattleInformation(cattle);
        Optional<Cattle> cattleOptional = cattleRepository.findByCattleIdAndEarTag(id, cattle.getEarTag());
        if (cattleOptional.isPresent()) {
            Cattle cattle1 = cattleOptional.get();
            cattle1.setBreedId(cattle.getBreedId());
            cattle1.setCategoryId(cattle.getCategoryId());
            cattle1.setSourceMarketId(cattle.getSourceMarketId());
            cattle1.setDateOfBirth(cattle.getDateOfBirth());
            final Cattle savedCattle = cattleRepository.save(cattle1);
            return mapToDto(savedCattle); // Map the saved Cattle to DTO
        }
        return null;
    }

//    @Override
//    public List<CattleDto> saveCattleBatch(List<Cattle> cattleList) throws Exception {
//        List<CattleDto> savedCattleDtos = new ArrayList<>();
//
//        // Loop through each cattle and save it
//        for (Cattle cattle : cattleList) {
//            validateCattleInformation(cattle);
//            final long nextInductionId = cattleRepository.getNextSequenceValue();
//            cattle.setId(nextInductionId);
//            final Cattle savedCattle = cattleRepository.save(cattle);
//            savedCattleDtos.add(mapToDto(savedCattle));
//        }
//
//        return savedCattleDtos;
//    }


    @Override
    public CattleDto saveCattle(final Cattle cattle) throws Exception {
        validateCattleInformation(cattle);
        final long nextInductionId = cattleRepository.getNextSequenceValue();
        cattle.setId(nextInductionId);
        final Cattle savedCattle = cattleRepository.save(cattle);
        return mapToDto(savedCattle);
    }



    @Override
    public List<String> findEarTagsWithIncompleteInduction(Long farmId) {
        return cattleRepository.findEarTagsWithIncompleteInduction(farmId);
    }

    private CattleDto mapToDto(final Cattle cattle) {

        Double weight = getLatestWeight(cattle.getCattleId());
        String cattleName = getCattleMarketName(cattle.getSourceMarketId());
        String breedAbbr = getBreedAbbr(cattle.getBreedId());
        String categoryDesc = getCategoryDesc(cattle.getCategoryId());
        String farmName = getFarmName(cattle.getFarmId());
        String agentName = getAgentName(cattle.getAgentId());
        Long treatmentCount = getCattleTotalTreatmentCount(cattle.getCattleId());
        String fatteningForName = getFatteningFor(cattle.getFatteningFor());
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
                cattle.getFatteningFor(),
                fatteningForName,
                cattle.getAgentId(),
                agentName,
                cattle.getConditionScore(),
                cattle.getHealthScore(),
                cattle.getWeightAtSale(),
                weight.toString(),
//                cattle.getBodyWeight(),
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

    private Double getLatestWeight(Long cattleId) {
        if (cattleId == null) {
            return 0.0;
        }

        WeightHistory latestWeightHistory = weightHistoryService.getLatestWeightHistory(cattleId);
        return (latestWeightHistory != null) ? latestWeightHistory.getWeight() : 0.0;
    }

    private String getCattleMarketName(Integer sourceMarketId) {
        return (sourceMarketId != null)
                ? marketService.getMarketById(sourceMarketId.longValue()).getMarketName()
                : null;
    }

    private String getFatteningFor(Integer fatteningForId) {
        return (fatteningForId != null)
                ? customerService.getCustomerById(fatteningForId.longValue()).getCustomerName()
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
