package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.service.AgentService;
import com.example.utapCattle.service.repository.AgentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public List<AgentDto> getAllAgents() {
        return agentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AgentDto getAgentById(Long id) {
        Optional<Agent> agent = agentRepository.findById(id);
        return agent.map(this::mapToDto).orElse(null);
    }

    @Override
    public AgentDto saveAgent(Agent agent) {
        Agent savedAgent = agentRepository.save(agent);
        return mapToDto(savedAgent);
    }

        // Helper method to map Agent to AgentDto
    private AgentDto mapToDto(Agent agent) {
        return new AgentDto(
                agent.getAgentId(),
                agent.getAgentName()
        );
    }
}
