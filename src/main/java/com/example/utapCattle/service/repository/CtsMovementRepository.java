package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.CtsMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CtsMovementRepository extends JpaRepository<CtsMovement, Long> {
    boolean existsByHashkey(String hashkey);
}
