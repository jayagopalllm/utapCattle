package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    String QUERY_MARKETS_BY_USER_ID = """
            SELECT DISTINCT m.*
            FROM market_farm_mapping mfm
            INNER JOIN market m ON m.marketid = mfm.marketid
            INNER JOIN users usr ON usr.farmid = mfm.farmid
            WHERE usr.id = :userId
            ORDER BY m.marketname
            """;

    @Query(value = QUERY_MARKETS_BY_USER_ID, nativeQuery = true)
    List<Market> findAllMarketsByUserId(@Param("userId") Long userId);

}
