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
    public List<AgentDto> getAllAgents(Long userFarmId) {
        return agentRepository.findAllByUserFarmId(userFarmId).stream()
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
                agent.getAgentName());
    }

    @Override
    public AgentDto update(Long id, Agent condition) {

        return agentRepository.findById(id).map(existingCondition -> {
            existingCondition.setAgentName(condition.getAgentName());
            agentRepository.save(existingCondition);
            return mapToDto(existingCondition);
        }).orElseThrow(() -> new RuntimeException("Compolsory Treatment not found"));

    }

    @Override
    public void delete(Long id) {
        if (agentRepository.existsById(id)) {
            agentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Compolsory Treatment not found");
        }
    }

}
