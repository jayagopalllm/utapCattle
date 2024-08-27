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
		final Optional<List<Cattle>> cattleList = cattleRepository.getEIdEartagMap();
		final Map<Long, String> eIdEarTagMap = new HashMap<>();
		if (cattleList.isPresent()) {
			cattleList.get().forEach(cattle -> {
				eIdEarTagMap.put(cattle.getCattleId(), cattle.getEarTag());
			});
		}
		treatmentData.setEIdEarTagMap(eIdEarTagMap);
		return treatmentData;
	}

	private AllDataDto getInductionAndTreatmentData() {
		final List<MedicalCondition> medicalConditions = medicalConditionRepository.findAll();
		final List<Medication> medications = medicationRepository.findAll();
		final List<Pen> pens = penRepository.findAll();
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
