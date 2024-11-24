package com.example.utapCattle.service.impl;

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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DataServiceImpl implements DataService {

	private final FarmRepository farmRepository;
	private final BreedRepository breedRepository;
	private final MarketRepository marketRepository;
	private final CategoryRepository categoryRepository;
	private final AgentRepository agentRepository;
	private final CustomerRepository customerRepository;
	private final MedicalConditionRepository medicalConditionRepository;
	private final MedicationRepository medicationRepository;
	private final CattleRepository cattleRepository;
	private final PenRepository penRepository;
	private final DefaultTreatmentRepository defaultTreatmentRepository;

	public DataServiceImpl(FarmRepository farmRepository,
					BreedRepository breedRepository,
					MarketRepository marketRepository,
					CategoryRepository categoryRepository,
					AgentRepository agentRepository,
					CustomerRepository customerRepository,
					MedicalConditionRepository medicalConditionRepository,
					MedicationRepository medicationRepository,
					CattleRepository cattleRepository,
					PenRepository penRepository,
					DefaultTreatmentRepository defaultTreatmentRepository) {
		this.farmRepository = farmRepository;
		this.breedRepository = breedRepository;
		this.marketRepository = marketRepository;
		this.categoryRepository = categoryRepository;
		this.agentRepository = agentRepository;
		this.customerRepository = customerRepository;
		this.medicalConditionRepository = medicalConditionRepository;
		this.medicationRepository = medicationRepository;
		this.cattleRepository = cattleRepository;
		this.penRepository = penRepository;
		this.defaultTreatmentRepository = defaultTreatmentRepository;
	}

	@Override
	public AllDataDto getAllData() {
		final List<Farm> farms = farmRepository.findAll();
		final List<Breed> breeds = breedRepository.findAll();
		final List<Market> markets = marketRepository.findAll();
		final List<Category> categories = categoryRepository.findAll();
		final List<Agent> agents = agentRepository.findAll();
		final List<Customer> customers = customerRepository.findAll();

		return new AllDataDto().builder()
				.sourceFarm(farms)
				.breed(breeds)
				.market(markets)
				.category(categories)
				.agent(agents)
				.fatteningFor(customers)
				.build();
	}

	@Override
	public AllDataDto getInductionData() {
		final List<DefaultTreatment> defaultTreatments = defaultTreatmentRepository.findAll();
		final List<MedicalCondition> medicalConditions = medicalConditionRepository.findAll();
		final List<Medication> medications = medicationRepository.findAll();
		final List<Pen> pens = penRepository.findAll();
		final List<String> earTagList = cattleRepository.findEarTagsWithIncompleteInduction();
		final List<String> eIdList = cattleRepository.findEIdsWithIncompleteInduction();

		return new AllDataDto().builder()
				.medicalCondition(medicalConditions)
				.medication(medications)
				.pens(pens)
				.eIdList(eIdList)
				.earTagList(earTagList)
				.defaultTreatments(defaultTreatments).build();
	}

	@Override
	public AllDataDto getTreatmentData() {
		final List<MedicalCondition> medicalConditions = medicalConditionRepository.findAll();
		final List<Medication> medications = medicationRepository.findAll();
		final List<Pen> pens = penRepository.findAll();
		final List<String> earTagList = cattleRepository.findEarTagsWithIncompleteInduction();
		final List<String> eIdList = cattleRepository.findEIdsWithIncompleteInduction();
		final Map<Long, String> eIdEarTagMap = getEidEarTagMapping();

		return new AllDataDto().builder()
				.medicalCondition(medicalConditions)
				.medication(medications)
				.pens(pens)
				.eIdList(eIdList)
				.earTagList(earTagList)
				.eIdEarTagMap(eIdEarTagMap).build();
	}

	@Override
	public AllDataDto getWeightAndTBTestData() {
		final Map<Long, String> eIdEarTagMap = getEidEarTagMapping();
		final List<Pen> pens = penRepository.findAll();

		return new AllDataDto().builder()
				.pens(pens)
				.eIdEarTagMap(eIdEarTagMap).build();
	}

	@Override
	public AllDataDto getSalesData() {
		final Map<Long, String> eIdEarTagMap = getEidEarTagMapping();
		final List<Pen> pens = penRepository.findAll();
		final List<Market> markets = marketRepository.findAll();

		return new AllDataDto().builder()
				.eIdEarTagMap(eIdEarTagMap)
				.pens(pens)
				.market(markets).build();
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

}
