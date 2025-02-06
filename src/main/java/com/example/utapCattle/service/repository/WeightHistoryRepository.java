package com.example.utapCattle.service.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.utapCattle.model.dto.WeightHistDto;
import com.example.utapCattle.model.dto.WeightHistoryDto;
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

	@Query(value = "SELECT * FROM weighthistory WHERE cattleid = :cattleId ORDER BY weighthistoryid DESC LIMIT 1", nativeQuery = true)
	WeightHistory findLatestWeightByCattleId(@Param("cattleId") Long cattleId);


//	@Query(value = """
//    SELECT
//        c.eartag AS earTag,
//        c.cattleid AS cattleId,
//        w.weightdatetime AS weightDateTime,
//        w.weight AS weight
//    FROM cattle c
//    INNER JOIN weighthistory w
//        ON c.cattleid = w.cattleid
//        AND TO_DATE(w.weightdatetime, 'DD/MM/YYYY') = TO_DATE(:date, 'YYYY-MM-DD')
//    ORDER BY w.weighthistoryid DESC
//    """, nativeQuery = true)
//List<WeightHistory> findByWegAndWeightDateTime(@Param("formattedDate") String formattedDate);


	@Query(value = """
   SELECT
      c.eartag AS earTag,
      c.cattleid AS cattleId,
      w.weightdatetime AS weightDateTime,
      w.weight AS weight
   FROM cattle c
   INNER JOIN weighthistory w
       ON c.cattleid = w.cattleid
   WHERE TO_DATE(w.weightdatetime, 'YYYY-MM-DD') = TO_DATE(:date, 'YYYY-MM-DD')
   ORDER BY w.weightdatetime DESC
   """, nativeQuery = true)
	List<Object[]> findWeightHistoryWithDetails(@Param("date") String date);




//	@Query(value ="""
//    SELECT
//       c.eartag AS earTag,
//       c.cattleid AS cattleId,
//       w.weightdatetime AS weightDateTime,
//       w.weight AS weight
//    FROM cattle c
//    INNER JOIN weighthistory w
//        ON c.cattleid = w.cattleid
//    WHERE TO_DATE(w.weightdatetime, 'DD/MM/YYYY') = TO_DATE(:date, 'DD/MM/YYYY')
//    """, nativeQuery = true)
//	List<WeightHistDto> findWeightHistoryWithDetails(@Param("date") String date);


//	@Query(value ="""
//    SELECT
//       c.eartag AS earTag,
//       c.cattleid AS cattleId,
//       w.weightdatetime AS weightDateTime,
//       w.weight AS weight
//    FROM cattle c
//    INNER JOIN weighthistory w
//        ON c.cattleid = w.cattleid
//    WHERE TO_DATE(w.weightdatetime, 'DD-MM-YYYY') = TO_DATE(:date, 'YYYY-MM-DD')
//""", nativeQuery = true)
//	List<WeightHistDto> findWeightHistoryWithDetails(@Param("date") String date);



}
