package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.Agent; // Note the import for Agent, not Owner

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {}
