package com.example.utapCattle.service.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.utapCattle.model.dto.TreatmentHistoryResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.TreatmentHistory;

@Repository
public interface TreatmentHistoryRepository extends JpaRepository<TreatmentHistory, Long> {

	@Query(value = "SELECT nextval('induction_id_seq')", nativeQuery = true)
	Long getNextSequenceValue();

	@Query("SELECT t FROM TreatmentHistory t WHERE t.cattleId = :cattleId")
	List<TreatmentHistory> findByCattleId(@Param("cattleId") Long cattleId);

	@Query("SELECT COUNT(t) FROM TreatmentHistory t WHERE t.cattleId = :cattleId")
	Long countByCattleId(@Param("cattleId") Long cattleId);

	@Query("SELECT t.withdrawalDate FROM TreatmentHistory t WHERE t.cattleId = :cattleId ORDER BY t.withdrawalDate DESC LIMIT 1")
	Optional<Date> findLatestWithdrawalDateByCattleId(@Param("cattleId") Long cattleId);


	@Query("SELECT AVG(t.conditionScore) FROM TreatmentHistory t WHERE t.cattleId = :cattleId")
	Double findAverageConditionScoreByCattleId(@Param("cattleId") Long cattleId);

	@Query("""
        SELECT new com.example.utapCattle.model.dto.TreatmentHistoryResponseDto(
            t.treatmentHistoryId,
            t.cattleId,
            m.conditionDesc,
            med.medicationDesc,
            t.batchNumber,
            t.treatmentDate,
            t.withdrawalDate
        )
        FROM TreatmentHistory t
        INNER JOIN Cattle c ON c.cattleId = t.cattleId
        INNER JOIN MedicalCondition m ON t.medicalConditionId = m.medicalConditionId
        INNER JOIN Medication med ON t.medicationId = med.medicationId
        WHERE t.cattleId = :eid AND c.saleId IS NULL
        order by t.treatmentDate desc
        """)
	List<TreatmentHistoryResponseDto> findTreatmentHistoryByCattleId(@Param("eid") Long eid);





}
