package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Pen;

@Repository
public interface PenRepository extends JpaRepository<Pen, Long> {

    List<Pen> findAllByUserFarmId(Long userFarmId);
}
