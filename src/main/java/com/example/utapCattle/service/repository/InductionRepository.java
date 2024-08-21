package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Induction;

@Repository
public interface InductionRepository extends JpaRepository<Induction, Long> {

	@Query(value = "SELECT nextval('induction_id_seq')", nativeQuery = true)
	Long getNextSequenceValue();
}
