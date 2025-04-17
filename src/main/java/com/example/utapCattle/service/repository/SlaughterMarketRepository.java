package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.SlaughterMarket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaughterMarketRepository extends JpaRepository<SlaughterMarket, Long> {

        List<SlaughterMarket> findByUserFarmIdOrderByMarketNameAsc(Long userFarmId);


}
