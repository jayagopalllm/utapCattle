package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.model.entity.Agent;

public interface AgentService {

    List<AgentDto> getAllAgents();

    AgentDto getAgentById(Long id);

    AgentDto saveAgent(Agent agent);

    List<Agent> findAgentForUser(Long userId);
}
