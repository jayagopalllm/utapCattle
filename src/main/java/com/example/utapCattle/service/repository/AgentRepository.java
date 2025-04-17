package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    List<Agent> findByUserFarmIdOrderByAgentNameAsc(Long userFarmId);

}
