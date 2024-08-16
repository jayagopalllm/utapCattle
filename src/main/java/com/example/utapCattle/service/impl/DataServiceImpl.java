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
import com.example.utapCattle.service.DataService;
import com.example.utapCattle.service.repository.AgentRepository;
import com.example.utapCattle.service.repository.BreedRepository;
import com.example.utapCattle.service.repository.CategoryRepository;
import com.example.utapCattle.service.repository.CustomerRepository;
import com.example.utapCattle.service.repository.FarmRepository;
import com.example.utapCattle.service.repository.MarketRepository;

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

@Override
public AllDataDto getAllData() {
    List<Farm> farms = farmRepository.findAll();
    List<Breed> breeds = breedRepository.findAll();
    List<Market> markets = marketRepository.findAll();
    List<Category> categories = categoryRepository.findAll();
    List<Agent> agents = agentRepository.findAll();
    List<Customer> customers = customerRepository.findAll();

    return new AllDataDto(farms, breeds, markets, categories, agents, customers);
}
    
}
