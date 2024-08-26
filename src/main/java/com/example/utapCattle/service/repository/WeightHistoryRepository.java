package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.WeightHistory;

@Repository
public interface WeightHistoryRepository extends JpaRepository<WeightHistory, Long> {

	@Query(value = "SELECT nextval('weight_seq')", nativeQuery = true)
	Long getNextSequenceValue();
}
