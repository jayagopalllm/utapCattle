package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.model.entity.Sale;
import com.example.utapCattle.model.entity.SaleDateRequest;
import com.example.utapCattle.model.entity.SaleTotalStats;

public interface SaleService {
    SaleDto sellCattle(final SaleDto saleDto);

    List<Sale> getExistingSaleDates(Long saleMarketId);

    Sale getSaleBySaleId(Long saleId);

    SaleTotalStats getSaleTotalStats(Long saleId);

    Boolean checkForValidSaleDate(SaleDateRequest request);
}