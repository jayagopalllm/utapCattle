package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.service.FarmService;
import com.example.utapCattle.service.repository.FarmRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmServiceImpl implements FarmService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final FarmRepository farmRepository;

    public FarmServiceImpl(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @Override
    public List<FarmDto> getAllFarms() {
        return farmRepository.findAll().stream()
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
                farm.getCurrent()
        );
    }

    @Override
    public List<Farm> findFarmForUser(Long userId) {
        String query = """
                SELECT distinct f.farmid  as farm_id, f.farmname as farm_name from source_farm_mapping sfm
                inner join farm f on f.farmid = sfm.source_farm_id
                inner join  users usr on usr.farmid=sfm.farmid
                where usr.id = ?
                """;
        List<Farm> farms = jdbcTemplate.query(query, new RowMapper<Farm>() {
            @Override
            public Farm mapRow(ResultSet rs, int rowNum) throws SQLException {
                Farm farm = new Farm();
                farm.setFarmId(rs.getLong("farm_id"));
                farm.setFarmName(rs.getString("farm_name"));
                return farm;
            }
        }, userId);

        return farms;
    }
}
