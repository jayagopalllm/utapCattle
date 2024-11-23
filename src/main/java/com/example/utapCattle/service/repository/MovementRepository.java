package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Movement;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

	@Query(value = "SELECT nextval('movement_seq')", nativeQuery = true)
	Long getNextSequenceValue();

	@Query("SELECT DISTINCT m.cattleId FROM Movement m WHERE m.penId = :penId")
	List<Long> findCattleIdsByPenId(@Param("penId") final Long penId);
}
