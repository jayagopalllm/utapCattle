package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Sale;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "SELECT nextval('sale_seq')", nativeQuery = true)
	Long getNextSequenceValue();

	@Query("SELECT s FROM Sale s WHERE s.saleMarketId = :saleMarketId AND s.saleDate = :saleDate")
	List<Sale> findSalesByMarketIdAndDate(@Param("saleMarketId") Long saleMarketId,
										  @Param("saleDate") String saleDate);

    List<Sale> findAllBySaleMarketId(Long saleMarketId);	

	Sale findBySaleId(Long saleId);

}