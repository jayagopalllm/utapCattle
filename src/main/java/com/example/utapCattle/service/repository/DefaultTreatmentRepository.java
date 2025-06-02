package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.dto.DefaultTreatmentDto;
import com.example.utapCattle.model.entity.DefaultTreatment;

@Repository
public interface DefaultTreatmentRepository extends JpaRepository<DefaultTreatment, Long> {

    @Query(value = "SELECT nextval('compulsorytreatment_seq')", nativeQuery = true)
    Long getNextSequenceValue();

    @Query("SELECT new com.example.utapCattle.model.dto.DefaultTreatmentDto(dt.compulsoryTreatmentId, dt.description, dt.medicalConditionId, dt.medicationId, m.batchNumber) " +
           "FROM DefaultTreatment dt JOIN Medication m ON dt.medicationId = m.medicationId")
    List<DefaultTreatmentDto> findAllWithBatchNumber();

}
