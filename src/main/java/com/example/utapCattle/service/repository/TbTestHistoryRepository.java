package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.TbTestHistory;

import java.util.Optional;

@Repository
public interface TbTestHistoryRepository extends JpaRepository<TbTestHistory, Integer> {

	@Query(value = "SELECT nextval('tbtest_seq')", nativeQuery = true)
	Long getNextSequenceValue();

	@Query("SELECT t FROM TbTestHistory t WHERE t.cattleId = :eid AND t.measA1 IS NOT NULL AND t.measB1 IS NOT NULL AND t.measA2 IS NULL AND t.measB2 IS NULL ORDER BY t.testDate DESC")
	Optional<TbTestHistory> getLatestTBTest(@Param("eid") Long eid);

}
