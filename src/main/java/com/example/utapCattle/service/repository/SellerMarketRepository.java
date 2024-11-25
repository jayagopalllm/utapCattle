package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.model.entity.SellerMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerMarketRepository extends JpaRepository<SellerMarket, Long> {
    
}
