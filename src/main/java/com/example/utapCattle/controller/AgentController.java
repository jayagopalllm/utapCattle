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
@RequestMapping("/agent")
public class AgentController extends BaseController{

    @Autowired
    private AgentService agentService;

    @GetMapping("/{id}")
    public ResponseEntity<AgentDto> getAgentById(@PathVariable Long id) {
        logger.info("Incoming request: Retrieving agent with ID: {}", id);
        AgentDto agent = agentService.getAgentById(id);
        if (agent != null) {
            logger.info("Request successful: Retrieved agent with ID: {}", id);
            return ResponseEntity.ok(agent);
        } else {
            logger.warn("Request failed: Agent not found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<AgentDto> getAllAgents() {
        logger.info("Incoming request: Retrieving all agents");
        List<AgentDto> agents = agentService.getAllAgents();
        logger.info("Request successful: Retrieved {} agents", agents.size());
        return agents;
    }

     @PostMapping("/save") 
    public ResponseEntity<AgentDto> saveAgent(@RequestBody Agent agent) {
        logger.info("Incoming request: Saving new agent: {}", agent);
        AgentDto savedAgentDto = agentService.saveAgent(agent);
        logger.info("Request successful: Saved agent with ID: {}", savedAgentDto.getAgentId());
        return new ResponseEntity<>(savedAgentDto, HttpStatus.CREATED);
    }

}
