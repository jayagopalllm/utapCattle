package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.model.entity.Agent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgentMapper {
    AgentDto toDto(Agent agent);

    Agent toEntity(AgentDto agentDto);
}