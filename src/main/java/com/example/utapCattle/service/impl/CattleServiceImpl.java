package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.service.CattleService;
import com.example.utapCattle.service.repository.CattleRepository;

@Service
public class CattleServiceImpl implements CattleService {

    @Autowired
    private CattleRepository cattleRepository;

    @Override
    public List<CattleDto> getAllCattle() { // Return List<CattleDto>
        return cattleRepository.findAll().stream()
                .map(this::mapToDto) // Map each Cattle to CattleDto
                .collect(Collectors.toList());
    }

    @Override
    public CattleDto getCattleById(Long id) { // Return CattleDto
        Optional<Cattle> cattle = cattleRepository.findById(id);
        return cattle.map(this::mapToDto).orElse(null); // Map to DTO if found
    }

    @Override
    public CattleDto saveCattle(Cattle cattle) { // Return CattleDto
        Cattle savedCattle = cattleRepository.save(cattle);
        return mapToDto(savedCattle); // Map the saved Cattle to DTO
    }

    private CattleDto mapToDto(Cattle cattle) {
        return new CattleDto(
                cattle.getCattleId(),
                cattle.getEarTag(),
                cattle.getTagId(),
                cattle.getNewTagReqd(),
                cattle.getDateOfBirth(),
                cattle.getMotherEarTag(),
                cattle.getBreedId(),
                cattle.getCategoryId(),
                cattle.getFarmId(),
                cattle.getSourceMarketId(),
                cattle.getCattleGroupId(),
                cattle.getConformationId(),
                cattle.getFatCoverId(),
                cattle.getConditionScore(),
                cattle.getHealthScore(),
                cattle.getDatePurchased(),
                cattle.getWeightAtPurchase(),
                cattle.getPurchasePrice(),
                cattle.getNumPrevMovements(),
                cattle.getSaleId(),
                cattle.getWeightAtSale(),
                cattle.getBodyWeight(),
                cattle.getSalePrice(),
                cattle.getExpenses(),
                cattle.getComments(),
                cattle.getSireEarTag(),
                cattle.getPreviousHolding(),
                cattle.getFatteningFor(),
                cattle.getAgentId(),
                cattle.getSireName(),
                cattle.getPoundPerKgGain(),
                cattle.getHdDayFeeders(),
                cattle.getTagOrdered(),
                cattle.getTagHere(),
                cattle.getCoopOpening(),
                cattle.getCoopClosing(),
                cattle.getResidencies(),
                cattle.getNewtagreq()
        );
    }
}
