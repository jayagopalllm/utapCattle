package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> { 
    
}
