package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.Agent; // Import your Agent entity

public interface AgentService {
    
    List<Agent> getAllAgents();

    Agent getAgentById(Long id);
}
