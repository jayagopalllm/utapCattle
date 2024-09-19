package com.example.utapCattle.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.AllDataDto;
import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.model.entity.Category;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.model.entity.DefaultTreatment;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.model.entity.MedicalCondition;
import com.example.utapCattle.model.entity.Medication;
import com.example.utapCattle.model.entity.Pen;
import com.example.utapCattle.service.DataService;
import com.example.utapCattle.service.repository.AgentRepository;
import com.example.utapCattle.service.repository.BreedRepository;
import com.example.utapCattle.service.repository.CategoryRepository;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.CustomerRepository;
import com.example.utapCattle.service.repository.DefaultTreatmentRepository;
import com.example.utapCattle.service.repository.FarmRepository;
import com.example.utapCattle.service.repository.MarketRepository;
import com.example.utapCattle.service.repository.MedicalConditionRepository;
import com.example.utapCattle.service.repository.MedicationRepository;
import com.example.utapCattle.service.repository.PenRepository;

@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private BreedRepository breedRepository;

	@Autowired
	private MarketRepository marketRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private MedicalConditionRepository medicalConditionRepository;

	@Autowired
	private MedicationRepository medicationRepository;

	@Autowired
	private CattleRepository cattleRepository;

	@Autowired
	private PenRepository penRepository;

	@Autowired
	private DefaultTreatmentRepository defaultTreatmentRepository;

	@Override
	public AllDataDto getAllData() {
		final List<Farm> farms = farmRepository.findAll();
		final List<Breed> breeds = breedRepository.findAll();
		final List<Market> markets = marketRepository.findAll();
		final List<Category> categories = categoryRepository.findAll();
		final List<Agent> agents = agentRepository.findAll();
		final List<Customer> customers = customerRepository.findAll();

		return new AllDataDto(farms, breeds, markets, categories, agents, customers);
	}

	@Override
	public AllDataDto getMedicalConditionData() {
		final AllDataDto conditionData = getInductionAndTreatmentData();
		final List<DefaultTreatment> defaultTreatments = defaultTreatmentRepository.findAll();
		conditionData.setDefaultTreatments(defaultTreatments);
		return conditionData;
	}

	@Override
	public AllDataDto getTreatmentData() {
		final AllDataDto treatmentData = getInductionAndTreatmentData();
		final Map<Long, String> eIdEarTagMap = getEidEarTagMapping();
		treatmentData.setEIdEarTagMap(eIdEarTagMap);
		return treatmentData;
	}

	@Override
	public AllDataDto getWeightAndTBTestData() {
		final AllDataDto tbTestData = new AllDataDto();
		final Map<Long, String> eIdEarTagMap = getEidEarTagMapping();
		tbTestData.setEIdEarTagMap(eIdEarTagMap);

		final List<Pen> pens = penRepository.findAll();
		tbTestData.setPens(pens);
		return tbTestData;
	}

	@Override
	public AllDataDto getSalesData() {
		final AllDataDto salesData = new AllDataDto();
		// EID and Ear tag mapping
		final Map<Long, String> eIdEarTagMap = getEidEarTagMapping();
		salesData.setEIdEarTagMap(eIdEarTagMap);
		// Pens
		final List<Pen> pens = penRepository.findAll();
		salesData.setPens(pens);
		// Market
		final List<Market> markets = marketRepository.findAll();
		salesData.setMarket(markets);

		return salesData;
	}

	private Map<Long, String> getEidEarTagMapping() {
		final Optional<List<Cattle>> cattleList = cattleRepository.getEIdEartagMap();
		final Map<Long, String> eIdEarTagMap = new HashMap<>();
		if (cattleList.isPresent()) {
			cattleList.get().forEach(cattle -> {
				eIdEarTagMap.put(cattle.getCattleId(), cattle.getEarTag());
			});
		}
		return eIdEarTagMap;
	}

	private AllDataDto getInductionAndTreatmentData() {
		final List<MedicalCondition> medicalConditions = medicalConditionRepository.findAll();
		final List<Medication> medications = medicationRepository.findAll();
		final List<Pen> pens = penRepository.findAll(); // should pick pen based on the select criteria filter 
		final List<String> earTagList = cattleRepository.findEarTagsWithIncompleteInduction();
		final List<String> eIdList = cattleRepository.findEIdsWithIncompleteInduction();

		final AllDataDto conditionData = new AllDataDto();
		conditionData.setMedicalCondition(medicalConditions);
		conditionData.setMedication(medications);
		conditionData.setPens(pens);
		conditionData.setEIdList(eIdList);
		conditionData.setEarTagList(earTagList);

		return conditionData;
	}

}
