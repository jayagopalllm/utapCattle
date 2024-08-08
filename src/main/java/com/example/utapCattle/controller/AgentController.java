package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.Agent;
import com.example.utapCattle.service.AgentService;

@RestController
@RequestMapping("/agents")  // Base path for agent endpoints
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping("/{id}")  // Get agent by ID
    public ResponseEntity<Agent> getAgentById(@PathVariable Long id) {
        Agent agent = agentService.getAgentById(id);
        return (agent != null) ? ResponseEntity.ok(agent) : ResponseEntity.notFound().build();
    }

    @GetMapping  // Get all agents
    public List<Agent> getAllAgents() {
        return agentService.getAllAgents();
    }

    // Add more methods for creating, updating, and deleting agents if needed
}
