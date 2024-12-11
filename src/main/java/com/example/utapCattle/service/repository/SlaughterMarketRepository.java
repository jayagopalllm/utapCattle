package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.model.entity.SlaughterMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaughterMarketRepository extends JpaRepository<SlaughterMarket, Long> {
    
}
