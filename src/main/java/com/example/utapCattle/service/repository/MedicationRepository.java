package com.example.utapCattle.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query("SELECT m FROM Medication m WHERE m.userFarmId = :userFarmId ORDER BY m.medicationDesc ASC")
    List<Medication> findByUserFarmIdOrderByMedicationDescAsc(Long userFarmId);

    @Query("SELECT m.withdrawalPeriod FROM Medication m WHERE m.medicationId = :medicationId")
    Optional<Integer> findWithdrawalPeriodByMedicationId(@Param("medicationId") Long medicationId);



}
