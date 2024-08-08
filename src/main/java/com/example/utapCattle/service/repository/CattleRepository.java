package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.Cattle; // Make sure to import the correct model class

@Repository
public interface CattleRepository extends JpaRepository<Cattle, Long> { // Use Long as the ID type
}
