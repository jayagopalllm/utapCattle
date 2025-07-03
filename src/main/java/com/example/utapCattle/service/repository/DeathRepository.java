package com.example.utapCattle.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.DeathRecord;

@Repository
public interface DeathRepository extends JpaRepository<DeathRecord, Long> {

    Optional<DeathRecord> findByEarTag(String earTag);

}
