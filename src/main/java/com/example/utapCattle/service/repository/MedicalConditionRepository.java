package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.MedicalCondition;

@Repository
public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long> {

    List<MedicalCondition> findAllByUserFarmId(Long userFarmId);

    // Additional custom query methods can be defined here if needed
}
