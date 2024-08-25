package com.example.utapCattle.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Cattle;

@Repository
public interface CattleRepository extends JpaRepository<Cattle, Long> { // Use Long as the ID type

	@Query(value = "SELECT nextval('cattle_seq')", nativeQuery = true)
	Long getNextSequenceValue();

	@Query(value = "select distinct eartag from cattle where isinductioncompleted is null or isinductioncompleted  = false;", nativeQuery = true)
	List<String> findEarTagsWithIncompleteInduction();

	@Query(value = "select distinct cattleid from cattle where cattleid is not null;", nativeQuery = true)
	List<String> findEIdsWithIncompleteInduction();

	/*
	 * @Query(value = "SELECT * FROM cattle WHERE tagid = :tagId LIMIT 1",
	 * nativeQuery = true) Cattle findByEartag(@Param("tagId") Integer tagId);
	 */

	@Query("SELECT c FROM Cattle c WHERE c.earTag = :earTag")
	Optional<Cattle> findByEarTag(@Param("earTag") String earTag);
}
