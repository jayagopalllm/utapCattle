package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.WeightHistory;

@Repository
public interface WeightHistoryRepository extends JpaRepository<WeightHistory, Long> {

	@Query(value = "SELECT nextval('weight_seq')", nativeQuery = true)
	Long getNextSequenceValue();

	@Query("SELECT w FROM WeightHistory w WHERE w.cattleId = :cattleId ORDER BY w.weightDateTime DESC")
	List<WeightHistory> findByCattleIdOrderByWeightDateTime(@Param("cattleId") Long cattleId);

}
