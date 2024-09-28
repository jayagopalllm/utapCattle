package com.example.utapCattle.service.impl;

import com.example.utapCattle.mapper.AgentMapper;
import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.service.AgentService;
import com.example.utapCattle.service.repository.AgentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final AgentMapper agentMapper;

    public AgentServiceImpl(AgentRepository agentRepository,
                            AgentMapper agentMapper) {
        this.agentRepository = agentRepository;
        this.agentMapper = agentMapper;
    }

    @Override
    public List<AgentDto> getAllAgents() {
        return agentRepository.findAll().stream()
                .map(agentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AgentDto getAgentById(Long id) {
        Optional<Agent> agent = agentRepository.findById(id);
        return agent.map(agentMapper::toDto).orElse(null);
    }

    @Override
    public AgentDto saveAgent(Agent agent) {
        Agent savedAgent = agentRepository.save(agent);
        return agentMapper.toDto(savedAgent);
    }

}
