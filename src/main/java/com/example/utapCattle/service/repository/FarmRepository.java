package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Farm;

import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    Optional<Farm> findByFarmId(@Param("id") Long id);
}
