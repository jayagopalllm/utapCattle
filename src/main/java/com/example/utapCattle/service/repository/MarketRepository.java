package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Market;

import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
    Optional<Market> findByMarketId(@Param("id") Long id);
    
}
