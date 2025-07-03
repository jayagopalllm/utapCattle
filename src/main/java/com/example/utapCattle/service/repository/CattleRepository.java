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
			SELECT c.earTag FROM Cattle c
			     WHERE c.userFarmId = :userFarmId
				 AND c.saleId is null
			     AND (c.isInductionCompleted IS NULL OR c.isInductionCompleted = FALSE)
				""")
	List<String> findEarTagsWithIncompleteInduction(@Param("userFarmId") Long userFarmId);

	@Query(value = "select distinct c.cattleId from Cattle c where c.cattleId is not null and c.saleId is null and c.userFarmId = :userFarmId")
	List<String> findEIdsWithIncompleteInduction(@Param("userFarmId") Long userFarmId);

	Optional<Cattle> findByCattleId(Long cattleId);

	Optional<Cattle> findByCattleIdAndEarTag(Long cattleId, String earTag);

	/*
	 * @Query(value = "SELECT * FROM cattle WHERE tagid = :tagId LIMIT 1",
	 * nativeQuery = true) Cattle findByEartag(@Param("tagId") Integer tagId);
	 */

	@Query(value = "SELECT * FROM cattle WHERE eartag = :earTagOrEid LIMIT 1", nativeQuery = true)
	Optional<Cattle> findByEarTag(@Param("earTagOrEid") String earTagOrEid);

	@Query("""
			SELECT c FROM Cattle c
			     WHERE c.userFarmId = :userFarmId AND c.saleId is NULL
			     AND c.cattleId IS NOT NULL AND c.isInductionCompleted = TRUE
				""")
	Optional<List<Cattle>> getEIdEartagMap(@Param("userFarmId") Long userFarmId);

	@Query("""
			SELECT c.earTag FROM Cattle c
			     WHERE c.userFarmId = :userFarmId AND c.saleId is NULL
			     AND c.cattleId IS NOT NULL AND c.isInductionCompleted = TRUE AND (c.healthStatus != 'Died' OR c.healthStatus IS NULL)
				""")
	List<String> getOnFarmCattleEarTags(@Param("userFarmId") Long userFarmId);

	@Query(value = "SELECT * FROM cattle_API WHERE customer = :id", nativeQuery = true)
	List<Object[]> findCattleDataById(@Param("id") String id);

	@Query("SELECT c FROM Cattle c WHERE c.inductionDate = :inductionDate and c.userFarmId = :userFarmId")
	List<Cattle> findByInductionDate(@Param("inductionDate") LocalDate inductionDate,
			@Param("userFarmId") Long userFarmId);

	List<Cattle> findAllBySaleId(Long saleId);

	boolean existsByCattleId(Long cattleId);

}
