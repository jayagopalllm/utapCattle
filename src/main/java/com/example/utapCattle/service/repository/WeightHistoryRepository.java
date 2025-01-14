package com.example.utapCattle.service.repository;

import java.time.LocalDate;
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

	@Query("SELECT w FROM WeightHistory w WHERE w.cattleId = :cattleId ORDER BY w.weightHistoryId DESC")
	List<WeightHistory> findByCattleIdOrderByWeightId(@Param("cattleId") Long cattleId);

	@Query(value = """
        SELECT 
            c.eartag AS earTag, 
            c.cattleid AS cattleId, 
            w.weightdatetime AS weightDateTime, 
            w.weight AS weight 
        FROM cattle c
        INNER JOIN weighthistory w 
            ON c.cattleid = w.cattleid 
            AND w.weightdatetime::DATE = :date
        ORDER BY w.weighthistoryid DESC
        """, nativeQuery = true)
	List<Object[]> findWeightHistoryByDate(@Param("date") LocalDate date);




}
