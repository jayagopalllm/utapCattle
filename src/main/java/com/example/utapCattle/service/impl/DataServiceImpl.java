package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.AllDataDto;
import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.*;
import com.example.utapCattle.service.DataService;
import com.example.utapCattle.service.FarmService;
import com.example.utapCattle.service.MarketService;
import com.example.utapCattle.service.repository.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DataServiceImpl implements DataService {

    private final FarmRepository farmRepository;
    private final BreedRepository breedRepository;
    private final MarketService marketService;
    private final MarketRepository marketRepository;
    private final SlaughterMarketRepository slaughterMarketRepository;
    private final FilterRepository filterRepository;
    private final SellerMarketRepository sellerMarketRepository;
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final MedicalConditionRepository medicalConditionRepository;
    private final MedicationRepository medicationRepository;
    private final CattleRepository cattleRepository;
    private final PenRepository penRepository;
    private final DefaultTreatmentRepository defaultTreatmentRepository;
    private final AgentRepository agentRepository;
    private final FarmService farmService;

    public DataServiceImpl(
            FarmRepository farmRepository,
            FilterRepository filterRepository,
            BreedRepository breedRepository,
            MarketService marketService,
            MarketRepository marketRepository,
            SlaughterMarketRepository slaughterMarketRepository,
            SellerMarketRepository sellerMarketRepository,
            CategoryRepository categoryRepository,
            CustomerRepository customerRepository,
            MedicalConditionRepository medicalConditionRepository,
            MedicationRepository medicationRepository,
            CattleRepository cattleRepository,
            PenRepository penRepository,
            DefaultTreatmentRepository defaultTreatmentRepository,
            AgentRepository agentRepository,
            FarmService farmService) {

        this.farmRepository = farmRepository;
        this.breedRepository = breedRepository;
        this.marketService = marketService;
        this.marketRepository = marketRepository;
        this.slaughterMarketRepository = slaughterMarketRepository;
        this.sellerMarketRepository = sellerMarketRepository;
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.medicalConditionRepository = medicalConditionRepository;
        this.medicationRepository = medicationRepository;
        this.cattleRepository = cattleRepository;
        this.penRepository = penRepository;
        this.filterRepository = filterRepository;
        this.defaultTreatmentRepository = defaultTreatmentRepository;
        this.agentRepository = agentRepository;
        this.farmService = farmService;
    }

    @Override
    public AllDataDto getAllData(Long userFarmId) {
        final List<Farm> farms = farmRepository.findByUserFarmIdOrderByFarmNameAsc(userFarmId);;
        final List<Breed> breeds = breedRepository.findAll();
        final List<MarketDto> markets = marketService.findAllMarketsByUserId(userFarmId);
        final List<Category> categories = categoryRepository.findAll();
        final List<Agent> agents = agentRepository.findByUserFarmIdOrderByAgentNameAsc(userFarmId);
        final List<Customer> customers = customerRepository.findByUserFarmIdOrderByCustomerNameAsc(userFarmId);
        final List<Pen> pens = penRepository.findAll();
        final Map<Long, String> eIdEarTagMap = getEidEarTagMapping(userFarmId);

        final AllDataDto allData = new AllDataDto();
        allData.setEIdEarTagMap(eIdEarTagMap);
        allData.setSourceFarm(farms);
        allData.setBreed(breeds);
        allData.setMarket(markets);
        allData.setCategory(categories);
        allData.setAgent(agents);
        allData.setFatteningFor(customers);
        allData.setPens(pens);

        return allData;
    }

    @Override
    public AllDataDto getMedicalConditionData(Long userId, Long userFarmId) {
        final AllDataDto conditionData = getInductionAndTreatmentData(userId,userFarmId);
        final List<DefaultTreatment> defaultTreatments = defaultTreatmentRepository.findAll();
        conditionData.setDefaultTreatments(defaultTreatments);
        return conditionData;
    }

    @Override
    public AllDataDto getTreatmentData(Long userId, Long userFarmId) {
        final AllDataDto treatmentData = getInductionAndTreatmentData(userId,userFarmId);
        final Map<Long, String> eIdEarTagMap = getEidEarTagMapping(userFarmId);
        treatmentData.setEIdEarTagMap(eIdEarTagMap);
        return treatmentData;
    }

    @Override
    public AllDataDto getWeightAndTBTestData(Long userId, Long userFarmId) {
        final AllDataDto tbTestData = new AllDataDto();
        final Map<Long, String> eIdEarTagMap = getEidEarTagMapping(userFarmId);
        tbTestData.setEIdEarTagMap(eIdEarTagMap);

        final List<Pen> pens = penRepository.findAll();
        tbTestData.setPens(pens);
        return tbTestData;
    }

    @Override
    public AllDataDto getSalesData(Long userId, Long userFarmId) {
        final AllDataDto salesData = new AllDataDto();
        // EID and Ear tag mapping
        final Map<Long, String> eIdEarTagMap = getEidEarTagMapping(userFarmId);
        salesData.setEIdEarTagMap(eIdEarTagMap);
        // Pens
        final List<Pen> pens = penRepository.findAll();
        salesData.setPens(pens);
        // Market
        final List<SellerMarket> sellerMarkets = sellerMarketRepository.findByUserFarmIdOrderBySellerMarketNameAsc(userFarmId);
//		final List<Market> markets = marketRepository.findAll();
        salesData.setSellerMarket(sellerMarkets);
//		salesData.setMarket(markets);

        return salesData;
    }

    @Override
    public AllDataDto getRegisterUserData() {
        final AllDataDto salesData = new AllDataDto();
        final List<Farm> farms = farmRepository.findAll();
        final List<Customer> customers = customerRepository.findAll();
        salesData.setSourceFarm(farms);
        salesData.setFatteningFor(customers);
        return salesData;
    }

    @Override
    public AllDataDto getFilterData() {
        final AllDataDto salesData = new AllDataDto();
        final List<FilterCriteria> filterCriteria = filterRepository.findAll();
        salesData.setFilterCriteria(filterCriteria);
        return salesData;
    }

    //	private Map<Long, String> getEidEarTagMapping() {
//		final Optional<List<Cattle>> cattleList = cattleRepository.getEIdEartagMap();
//		final Map<Long, String> eIdEarTagMap = new HashMap<>();
//		if (cattleList.isPresent()) {
//			cattleList.get().forEach(cattle -> {
//				eIdEarTagMap.put(cattle.getCattleId(), cattle.getEarTag());
//			});
//		}
//		return eIdEarTagMap;
//	}
    @Override
    public AllDataDto getSlaughtersHouse(Long userFarmId) {
        final AllDataDto slaughterData = new AllDataDto();
        final List<SlaughterMarket> slaughterMarkets = slaughterMarketRepository.findByUserFarmIdOrderByMarketNameAsc(userFarmId);
        final List<Customer> customerList = customerRepository.findByUserFarmIdOrderByCustomerNameAsc(userFarmId);
        slaughterData.setSlaughterMarket(slaughterMarkets);
        slaughterData.setCustomers(customerList);
        return slaughterData;
    }

    private Map<Long, String> getEidEarTagMapping(Long userFarmId) {
        final Optional<List<Cattle>> cattleList = cattleRepository.getEIdEartagMap(userFarmId);
        final Map<Long, String> eIdEarTagMap = new HashMap<>();
        if (cattleList.isPresent()) {
            cattleList.get().forEach(cattle -> {
                eIdEarTagMap.put(cattle.getCattleId(), cattle.getEarTag());
            });
        }
        return eIdEarTagMap;
    }

    private AllDataDto getInductionAndTreatmentData(Long userId, Long userFarmId) {
        final List<MedicalCondition> medicalConditions = medicalConditionRepository.findAll();
        final List<Medication> medications = medicationRepository.findAll();
        final List<Pen> pens = penRepository.findAll(); // should pick pen based on the select criteria filter
        final List<String> earTagList = cattleRepository.findEarTagsWithIncompleteInduction(userFarmId);
        final List<String> eIdList = cattleRepository.findEIdsWithIncompleteInduction(userFarmId);

        final AllDataDto conditionData = new AllDataDto();
        conditionData.setMedicalCondition(medicalConditions);
        conditionData.setMedication(medications);
        conditionData.setPens(pens);
        conditionData.setEIdList(eIdList);
        conditionData.setEarTagList(earTagList);

        return conditionData;
    }

}
