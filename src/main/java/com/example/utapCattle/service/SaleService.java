package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.dto.SaleDateRequest;
import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.model.entity.Sale;
import com.example.utapCattle.model.entity.SaleTotalStats;

public interface SaleService {
    SaleDto sellCattle(final SaleDto saleDto, Long userId);

    SaleDto keepCattle(final SaleDto saleDto, Long userId);

    List<Sale> getExistingSaleDates(Long saleMarketId);

    Sale getSaleBySaleId(Long saleId);

    SaleTotalStats getSaleTotalStats(Long saleId);

    Boolean checkForValidSaleDate(SaleDateRequest request);

    List<CattleDto> getAllCattleBySaleId(Long saleId);
}
