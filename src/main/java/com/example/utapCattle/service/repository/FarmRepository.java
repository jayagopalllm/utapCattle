package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Farm;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    List<Farm> findByUserFarmIdOrderByFarmNameAsc(Long userFarmId);

}
