package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.WeightHistory;

import jakarta.persistence.Tuple;

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
            with WeightRanked as
               (
               select
                  cattleid,weight,
                  weightdatetime as date_weighted,
                  row_number() over (partition by cattleid order by weightdatetime::timestamp asc) as min_rank,
                  row_number() over (partition by cattleid order by weightdatetime::timestamp desc) as max_rank
               from
                  weighthistory
               where
                  weight > 25
               )

            select
            c.eartag,
            c.cattleid,
            w_max.weight,
            (w_max.weight-w_min.weight) / nullif((w_max.date_weighted::date - w_min.date_weighted::date ), 0) as dlwgfarm,
            w_max.date_weighted as weightdatetime
            from
            cattle c
            left join WeightRanked w_min on	c.cattleid = w_min.cattleid	and w_min.min_rank = 1
            left join WeightRanked w_max on	c.cattleid = w_max.cattleid	and w_max.max_rank = 1
            where
            TO_DATE(w_max.date_weighted, 'YYYY-MM-DD') = TO_DATE(:date, 'YYYY-MM-DD')
            AND  c.ownerfarmid= :userFarmId
            ORDER BY w_max.date_weighted::timestamp DESC
      """, nativeQuery = true)
   List<Tuple> findWeightHistoryWithDetails(@Param("date") String date, Long userFarmId);



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
