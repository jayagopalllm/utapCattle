package com.example.utapCattle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.AllDataDto;
import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.model.entity.Category;
import com.example.utapCattle.model.entity.Customer;
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
		final List<MedicalCondition> medicalConditions = medicalConditionRepository.findAll();
		final List<Medication> medications = medicationRepository.findAll();
		final List<Pen> pens = penRepository.findAll();
		final List<String> earTagList = cattleRepository.findEarTagsWithIncompleteInduction();

		return new AllDataDto(medicalConditions, medications, pens, earTagList);
	}

}
