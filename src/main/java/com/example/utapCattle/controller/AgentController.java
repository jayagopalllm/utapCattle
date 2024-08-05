package com.example.utapCattle.controller;

import com.example.utapCattle.model.Agent;
import com.example.utapCattle.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
