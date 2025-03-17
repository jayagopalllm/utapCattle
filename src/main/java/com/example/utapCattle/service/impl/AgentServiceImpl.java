package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.service.AgentService;
import com.example.utapCattle.service.repository.AgentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public List<AgentDto> getAllAgents() {
        return agentRepository.findAll().stream()
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

    @Override
    public List<Agent> findAgentForUser(Long userId) {

        String query = """
                SELECT distinct afm.agentid  as agent_id, a.agentname as agent_name from agent_farm_mapping afm
                inner join agent a on a.agentid = afm.agentid
                inner join  users usr on usr.farmid=afm.farmid
                where usr.id = ?
                """;
        List<Agent> agents = jdbcTemplate.query(query, new RowMapper<Agent>() {
            @Override
            public Agent mapRow(ResultSet rs, int rowNum) throws SQLException {
                Agent agent = new Agent();
                agent.setAgentId(rs.getLong("agent_id"));
                agent.setAgentName(rs.getString("agent_name"));
                return agent;
            }
        }, userId);
        return agents;

    }

        // Helper method to map Agent to AgentDto
    private AgentDto mapToDto(Agent agent) {
        return new AgentDto(
                agent.getAgentId(),
                agent.getAgentName()
        );
    }
}
