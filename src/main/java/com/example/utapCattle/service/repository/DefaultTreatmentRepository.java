package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.DefaultTreatment;

@Repository
public interface DefaultTreatmentRepository extends JpaRepository<DefaultTreatment, Long> {

}