package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.service.FarmService;
import com.example.utapCattle.service.repository.FarmRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;

    public FarmServiceImpl(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @Override
    public List<FarmDto> getAllFarms(Long userFarmId) {
        return farmRepository.findByUserFarmIdOrderByFarmNameAsc(userFarmId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FarmDto getFarmById(Long id) {
        Optional<Farm> farm = farmRepository.findById(id);
        return farm.map(this::mapToDto).orElse(null);
    }

    @Override
    public FarmDto saveFarm(Farm farm) {
        Farm savedFarm = farmRepository.save(farm);
        return mapToDto(savedFarm);
    }

    // Helper method to map Farm to FarmDto
    private FarmDto mapToDto(Farm farm) {
        return new FarmDto(
                farm.getFarmId(),
                farm.getFarmContact(),
                farm.getFarmName(),
                farm.getAddress(),
                farm.getHoldingNumber(),
                farm.getAssuranceNumber(),
                farm.getAssuranceExpiryDate(),
                farm.getCounty(),
                farm.getPostcode(),
                farm.getFarmRef(),
                farm.getCurrent());
    }

    @Override
    public FarmDto update(Long id, Farm updatedFarm) {
        return farmRepository.findById(id).map(farm -> {
            farm.setFarmContact(updatedFarm.getFarmContact());
            farm.setFarmName(updatedFarm.getFarmName());
            farm.setAddress(updatedFarm.getAddress());
            farm.setHoldingNumber(updatedFarm.getHoldingNumber());
            farm.setAssuranceNumber(updatedFarm.getAssuranceNumber());
            farm.setAssuranceExpiryDate(updatedFarm.getAssuranceExpiryDate());
            farm.setCounty(updatedFarm.getCounty());
            farm.setPostcode(updatedFarm.getPostcode());
            farm.setFarmRef(updatedFarm.getFarmRef());
            farm.setCurrent(updatedFarm.getCurrent());
            return mapToDto(farmRepository.save(farm));
        }).orElseThrow(() -> new RuntimeException("Farm not found"));
    }

    @Override
    public void delete(Long id) {
        if (farmRepository.existsById(id)) {
            farmRepository.deleteById(id);
        } else {
            throw new RuntimeException("Farm not found");
        }
    }

}
