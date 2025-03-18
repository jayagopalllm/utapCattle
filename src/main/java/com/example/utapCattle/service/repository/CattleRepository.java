package com.example.utapCattle.service.repository;

import java.time.LocalDate;
import java.util.Date;
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

	@Query("""
			SELECT DISTINCT c.earTag FROM Cattle c
			     WHERE c.ownerFarmId = :ownerFarmId
			     AND (c.isInductionCompleted IS NULL OR c.isInductionCompleted = FALSE)
				""")
	List<String> findEarTagsWithIncompleteInduction(@Param("ownerFarmId") Long ownerFarmId);

	@Query(value = "select distinct cattleid from cattle where cattleid is not null;", nativeQuery = true)
	List<String> findEIdsWithIncompleteInduction();

	Optional<Cattle> findByCattleId(Long cattleId);

	Optional<Cattle> findByCattleIdAndEarTag(Long cattleId, String earTag);

	/*
	 * @Query(value = "SELECT * FROM cattle WHERE tagid = :tagId LIMIT 1",
	 * nativeQuery = true) Cattle findByEartag(@Param("tagId") Integer tagId);
	 */


	@Query(value = "SELECT * FROM cattle WHERE eartag = :earTagOrEid LIMIT 1", nativeQuery = true)
	Optional<Cattle> findByEarTag(@Param("earTagOrEid") String earTagOrEid);

	@Query("SELECT c FROM Cattle c WHERE c.cattleId IS NOT NULL")
	Optional<List<Cattle>> getEIdEartagMap();

	@Query(value = "SELECT * FROM cattle_API WHERE customer = :id", nativeQuery = true)
	List<Object[]> findCattleDataById(@Param("id") String id);

	@Query("SELECT c FROM Cattle c WHERE c.inductionDate = :inductionDate")
	List<Cattle> findByInductionDate(@Param("inductionDate") LocalDate inductionDate);


}
