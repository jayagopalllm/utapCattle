package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Cattle;

@Repository
public interface CattleRepository extends JpaRepository<Cattle, Long> { // Use Long as the ID type

	@Query(value = "SELECT nextval('cattle_seq')", nativeQuery = true)
	Long getNextSequenceValue();

	@Query(value = "select distinct eartag from cattle where isinductioncompleted is null or isinductioncompleted  = false;", nativeQuery = true)
	List<String> getEarTagList();
}
