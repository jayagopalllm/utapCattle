package com.example.utapCattle.service;

import com.example.utapCattle.mapper.AgentMapper;
import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.service.impl.AgentServiceImpl;
import com.example.utapCattle.service.repository.AgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AgentServiceTest {

    @Mock
    private AgentRepository agentRepository;
    private AgentMapper agentMapper = Mappers.getMapper(AgentMapper.class);
    private AgentService agentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        agentService = new AgentServiceImpl(agentRepository, agentMapper);
    }

    @Test
    void fetchAllAgent_WhenAgentExists_ShouldReturnAllAgents() {
        Agent agent1 = new Agent(1L, "Agent1");
        Agent agent2 = new Agent(2L, "Agent2");
        List<Agent> agentList = Arrays.asList(agent1, agent2);

        when(agentRepository.findAll()).thenReturn(agentList);

        List<AgentDto> result = agentService.getAllAgents();
        assertEquals(2, result.size());
    }

    @Test
    public void fetchAgentById_WhenAgentExists_ShouldReturnAgent() {
        Agent agent = new Agent(1L, "AgentA");
        AgentDto agentDto = agentMapper.toDto(agent);

        when(agentRepository.findById(1L)).thenReturn(Optional.of(agent));

        AgentDto result = agentService.getAgentById(1L);
        assertEquals(result, agentDto);
    }

    @Test
    public void fetchSavedAgent_WhenAgentIsSaved_ShouldReturnAgent() {
        Agent agent = new Agent(1L, "Agent1");
        AgentDto agentDto = agentMapper.toDto(agent);

        when(agentRepository.save(any(Agent.class))).thenReturn(agent);

        AgentDto result = agentService.saveAgent(agent);
        assertEquals(result, agentDto);
    }


}
