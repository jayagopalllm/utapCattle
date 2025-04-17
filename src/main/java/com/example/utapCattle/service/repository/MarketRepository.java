package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    List<Market> findByUserFarmIdOrderByMarketNameAsc(Long userFarmId);


}
