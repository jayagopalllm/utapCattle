package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.TbTestHistory;

@Repository
public interface TbTestHistoryRepository extends JpaRepository<TbTestHistory, Integer> {

	@Query(value = "SELECT nextval('tbtest_seq')", nativeQuery = true)
	Long getNextSequenceValue();

}
