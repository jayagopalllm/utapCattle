package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.model.entity.SlaughterHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaughterRepository extends JpaRepository<SlaughterHouse, Long> {

    boolean existsByEarTag1AndCarcassNumber(String earTag1, Integer carcassNumber);
}
