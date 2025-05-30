package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.model.entity.Medication;
import com.example.utapCattle.service.AgentService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agent")
public class AgentController extends BaseController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentDto> getAgentById(@PathVariable Long id) {
        try {
            AgentDto agent = agentService.getAgentById(id);
            if (agent != null) {
                logger.info("Retrieved agent with ID: {}", id);
                return ResponseEntity.ok(agent);
            } else {
                logger.warn("Agent not found for ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve Agent with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AgentDto>> getAllAgents(HttpServletRequest request) {
        try {
            Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
            List<AgentDto> agents = agentService.getAllAgents(userFarmId);
            if (!agents.isEmpty()) {
                logger.info("Retrieved {} Agents", agents.size());
            } else {
                logger.warn("No Agents found");
            }
            return ResponseEntity.ok(agents);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve Agents", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveAgent(@RequestBody Agent agent) {
        logger.info("Saving new agent: {}", agent);
        try {
            AgentDto savedAgentDto = agentService.saveAgent(agent);
            logger.info("Saved Agent with ID: {}", savedAgentDto.getAgentId());
            return new ResponseEntity<>(savedAgentDto.getAgentId(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to save Agent", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentDto> update(@PathVariable Long id, @RequestBody Agent condition) {
        try {
            return ResponseEntity.ok(agentService.update(id, condition));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            agentService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
