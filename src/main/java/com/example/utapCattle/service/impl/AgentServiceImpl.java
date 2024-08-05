package com.example.utapCattle.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.utapCattle.model.Agent;  // Import your Agent entity
import com.example.utapCattle.service.AgentService; // Import your AgentService interface
import com.example.utapCattle.service.repository.AgentRepository; // Import AgentRepository

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository; // Inject the AgentRepository

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public Agent getAgentById(Long id) {
        return agentRepository.findById(id).orElse(null);
    }

    // Add more methods as needed for your business logic (e.g., createAgent, updateAgent, deleteAgent)
}
