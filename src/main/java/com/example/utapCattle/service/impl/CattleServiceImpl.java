package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.*;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.SellerMarketRepository;
import com.example.utapCattle.service.repository.TreatmentHistoryRepository;
import com.example.utapCattle.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CattleServiceImpl implements CattleService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SellerMarketRepository sellerMarketRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final CattleRepository cattleRepository;
    private final BreedService breedService;
    private final CategoryService categoryService;
    private final FarmService farmService;
    private final MarketService marketService;
    private final CustomerService customerService;
    private final AgentService agentService;
    private final TreatmentHistoryRepository treatmentHistoryRepository;
    private final WeightHistoryService weightHistoryService;
    private final SaleService saleService;

    public CattleServiceImpl(CattleRepository cattleRepository, BreedService breedService,
            CategoryService categoryService, FarmService farmService, MarketService marketService,
            CustomerService customerService, AgentService agentService,
            TreatmentHistoryRepository treatmentHistoryRepository, WeightHistoryService weightHistoryService,
            SaleService saleService) {
        this.cattleRepository = cattleRepository;
        this.breedService = breedService;
        this.categoryService = categoryService;
        this.farmService = farmService;
        this.marketService = marketService;
        this.customerService = customerService;
        this.agentService = agentService;
        this.treatmentHistoryRepository = treatmentHistoryRepository;
        this.weightHistoryService = weightHistoryService;
        this.saleService = saleService;
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
        final Optional<Cattle> cattle = cattleRepository.findByEarTag(earTag);
        return cattle.map(this::mapToDto).orElse(null);
    }

    @Override
    public CattleDto updateCattle(final Long id, final Cattle cattle) throws Exception { // Return CattleDto
        // validateCattleInformation(cattle);
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

    // @Override
    // public List<CattleDto> saveCattleBatch(List<Cattle> cattleList) throws
    // Exception {
    // List<CattleDto> savedCattleDtos = new ArrayList<>();
    //
    // // Loop through each cattle and save it
    // for (Cattle cattle : cattleList) {
    // validateCattleInformation(cattle);
    // final long nextInductionId = cattleRepository.getNextSequenceValue();
    // cattle.setId(nextInductionId);
    // final Cattle savedCattle = cattleRepository.save(cattle);
    // savedCattleDtos.add(mapToDto(savedCattle));
    // }
    //
    // return savedCattleDtos;
    // }

    @Override
    public CattleDto saveCattle(final Cattle cattle) throws Exception {
        validateCattleInformation(cattle);
        final long nextInductionId = cattleRepository.getNextSequenceValue();
        cattle.setId(nextInductionId);
        final Cattle savedCattle = cattleRepository.save(cattle);
        return mapToDto(savedCattle);
    }

    @Override
    public List<String> findEarTagsWithIncompleteInduction(Long userFarmId) {
        return cattleRepository.findEarTagsWithIncompleteInduction(userFarmId);
    }

    private CattleDto mapToDto(final Cattle cattle) {
        Double weight = getLatestWeight(cattle.getCattleId());
        String cattleName = getCattleMarketName(cattle.getSaleId());
        String breedAbbr = getBreedAbbr(cattle.getBreedId());
        String categoryDesc = getCategoryDesc(cattle.getCategoryId());
        String sex = getSex(cattle.getCategoryId());
        String farmName = getFarmName(cattle.getFarmId());
        String agentName = getAgentName(cattle.getAgentId());
        Long treatmentCount = getCattleTotalTreatmentCount(cattle.getCattleId());
        String fatteningForName = getFatteningFor(cattle.getFatteningFor());
        String lastTreatment = getLastWithdrawalDate(cattle.getCattleId());
        Double dlwgFarm = getDlwgFarm(cattle.getCattleId());
        CattleDto cattleData = new CattleDto();
        cattleData.setId(cattle.getId());
        cattleData.setCattleId(cattle.getCattleId());
        cattleData.setPrefix(cattle.getPrefix());
        cattleData.setEarTag(cattle.getEarTag());
        cattleData.setDateOfBirth(DateUtils.formatToReadableDate(cattle.getDateOfBirth(), "dd/MM/yyyy"));
        cattleData.setMotherEarTag(cattle.getMotherEarTag());
        cattleData.setBreedId(cattle.getBreedId());
        cattleData.setCategoryId(cattle.getCategoryId());
        cattleData.setBreedName(breedAbbr);
        cattleData.setCategoryName(categoryDesc);
        cattleData.setSex(sex);
        cattleData.setFarmId(cattle.getFarmId());
        cattleData.setFarmName(farmName);
        cattleData.setSourceMarketId(cattle.getSourceMarketId());
        cattleData.setSourceMarketName(cattleName);
        cattleData.setDatePurchased(cattle.getDatePurchased());
        cattleData.setPurchasePrice(cattle.getPurchasePrice());
        cattleData.setSaleId(cattle.getSaleId());
        cattleData.setSalePrice(cattle.getSalePrice());
        cattleData.setComments(cattle.getComments());
        cattleData.setVersion(cattle.getVersion());
        cattleData.setPreviousHolding(cattle.getPreviousHolding());
        cattleData.setFatteningFor(cattle.getFatteningFor());
        cattleData.setFatteningForName(fatteningForName);
        cattleData.setAgentId(cattle.getAgentId());
        cattleData.setAgentName(agentName);
        cattleData.setConditionScore(cattle.getConditionScore());
        cattleData.setHealthScore(cattle.getHealthScore());
        cattleData.setWeightAtSale(cattle.getWeightAtSale());
        cattleData.setDlwgFarm(dlwgFarm);
        cattleData.setBodyWeight(weight.toString());
        cattleData.setExpenses(cattle.getExpenses());
        cattleData.setSireEarTag(cattle.getSireEarTag());
        cattleData.setSireName(cattle.getSireName());
        cattleData.setPoundPerKgGain(cattle.getPoundPerKgGain());
        cattleData.setHdDayFeeders(cattle.getHdDayFeeders());
        cattleData.setTagOrdered(cattle.getTagOrdered());
        cattleData.setTagHere(cattle.getTagHere());
        cattleData.setCoopOpening(cattle.getCoopOpening());
        cattleData.setCoopClosing(cattle.getCoopClosing());
        cattleData.setResidencies(cattle.getResidencies());
        cattleData.setNewtagreq(cattle.getNewtagreq());
        cattleData.setTagId(cattle.getTagId());
        cattleData.setNewTagReqd(cattle.getNewTagReqd());
        cattleData.setCattleGroupId(cattle.getCattleGroupId());
        cattleData.setConformationId(cattle.getConformationId());
        cattleData.setFatCoverId(cattle.getFatCoverId());
        cattleData.setWeightAtPurchase(cattle.getWeightAtPurchase());
        cattleData.setNumPrevMovements(cattle.getNumPrevMovements());
        cattleData.setTotalTreatments(treatmentCount);
        cattleData.setLastWithdraw(lastTreatment);
        cattleData.setIsInductionCompleted(cattle.getIsInductionCompleted());
        return cattleData;
    }

    public String getLastWithdrawalDate(Long cattleId) {
        return (cattleId != null)
                ? treatmentHistoryRepository.findLatestWithdrawalDateByCattleId(cattleId)
                        // .map(Date::toString)
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

    private Double getDlwgFarm(Long cattleId) {
        if (cattleId == null) {
            return 0.0;
        }

        String query = """
                with WeightRanked as
                	(
                    select
                        cattleid,weight,
                        TO_DATE(weightdatetime, 'YYYY-MM-DD HH24:MI:SS') as date_weighted,
                        row_number() over (partition by cattleid order by TO_DATE(weightdatetime, 'YYYY-MM-DD HH24:MI:SS') asc) as min_rank,
                        row_number() over (partition by cattleid order by TO_DATE(weightdatetime, 'YYYY-MM-DD HH24:MI:SS') desc) as max_rank
                    from
                        weighthistory
                    where
                        weight > 25
                	)

                select
                	(w_max.weight-w_min.weight) / nullif((w_max.date_weighted - w_min.date_weighted), 0) as dlwgfarm
                from
                	cattle c
                left join WeightRanked w_min on	c.cattleid = w_min.cattleid	and w_min.min_rank = 1
                left join WeightRanked w_max on	c.cattleid = w_max.cattleid	and w_max.max_rank = 1
                where
                	c.cattleid = ?
                                """;

        Map<String, Object> result = jdbcTemplate.queryForMap(query, cattleId);
        Double dlwgFarm = (Double) result.get("dlwgfarm");

        // Round to 2 decimal places using String.format and then convert back to double
        return (dlwgFarm != null) ? Double.parseDouble(String.format("%.2f", dlwgFarm)) : 0.0;
    }

    private String getCattleMarketName(Long saleId) {
        return (saleId != null)
                ? sellerMarketRepository.findBySellerMarketId(saleService.getSaleBySaleId(saleId).getSaleMarketId())
                        .getSellerMarketName()
                // marketService.getMarketById(sourceMarketId.longValue()).getMarketName()
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

    private String getSex(Integer categoryId) {
        return (categoryId != null)
                ? categoryService.getCategoryById(categoryId.longValue()).getSex()
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
