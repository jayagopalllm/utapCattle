package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.service.AgentService;

@RestController
@RequestMapping("/agent")  // Base path for agent endpoints
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping("/{id}")  // Get agent by ID
    public ResponseEntity<AgentDto> getAgentById(@PathVariable Long id) {
        AgentDto agent = agentService.getAgentById(id);
        return (agent != null) ? ResponseEntity.ok(agent) : ResponseEntity.notFound().build();
    }

    @GetMapping  // Get all agents
    public List<AgentDto> getAllAgents() {
        return agentService.getAllAgents();
    }

     @PostMapping("/save") // Save a new agent
    public ResponseEntity<AgentDto> saveAgent(@RequestBody Agent agent) {
        AgentDto savedAgentDto = agentService.saveAgent(agent);
        return new ResponseEntity<>(savedAgentDto, HttpStatus.CREATED);
    }

    // Add more methods for creating, updating, and deleting agents if needed
}
