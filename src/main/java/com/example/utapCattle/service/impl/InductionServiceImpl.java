package com.example.utapCattle.service.impl;

import com.example.utapCattle.exception.DuplicateCattleException;
import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.TreatmentHistoryMetadata;
import com.example.utapCattle.service.InductionService;
import com.example.utapCattle.service.TreatmentHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InductionServiceImpl implements InductionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final CattleRepository cattleRepository;
    private final TreatmentHistoryService treatmentHistoryService;

    public InductionServiceImpl(CattleRepository cattleRepository, TreatmentHistoryService treatmentHistoryService) {
        this.cattleRepository = cattleRepository;
        this.treatmentHistoryService = treatmentHistoryService;
    }

    @Override
    public final List<CattleDto> getInductionList(final LocalDate date, final Long userFarmId) {

        String query = """
                WITH latest_weights AS (
                  SELECT DISTINCT ON (cattleid) cattleid, weight
                  FROM weighthistory
                  ORDER BY cattleid, TO_DATE(weightdatetime, 'YYYY-MM-DD') DESC
                )
                SELECT
                  c.id,
                  c.cattleid,
                  c.eartag,
                  c.dateofbirth,
                  c.breedid,
                  c.comments,
                  coalesce(lw.weight,0) AS bodyweight
                FROM cattle c
                LEFT JOIN latest_weights lw ON lw.cattleid = c.cattleid
                WHERE c.inductiondate = ? AND c.ownerfarmid = ? and c.isinductioncompleted = true
                ORDER BY c.updatedon DESC
                              """;
        List<CattleDto> cattleData = jdbcTemplate.query(query, new RowMapper<CattleDto>() {
            @Override
            public CattleDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                CattleDto cattleData = new CattleDto();
                cattleData.setCattleId(rs.getLong("cattleid"));
                cattleData.setEarTag(rs.getString("eartag"));
                cattleData.setDateOfBirth(rs.getString("dateofbirth"));
                cattleData.setBreedId(rs.getInt("breedid"));
                cattleData.setComments(rs.getString("comments"));
                cattleData.setBodyWeight(rs.getString("bodyweight"));
                return cattleData;
            }
        },date,userFarmId);

        return cattleData;
    }

    @Override
    public final Map<String, Object> saveInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata) {

        validateInduction(treatmentHistoryMetadata);

        treatmentHistoryMetadata.setProcessId(new SecureRandom().nextLong());

        updateCattleDetails(treatmentHistoryMetadata);

        return treatmentHistoryService
                .saveTreatmentHistory(treatmentHistoryMetadata);
    }

    private void validateInduction(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
        if (treatmentHistoryMetadata.getCattleId() == null) {
            throw new IllegalArgumentException("EId is a mandatory field and cannot be null or empty.");
        }
        if (StringUtils.isBlank(treatmentHistoryMetadata.getEarTag())) {
            throw new IllegalArgumentException("EarTag is a mandatory field and cannot be null or empty.");
        }
    }

    private void updateCattleDetails(final TreatmentHistoryMetadata treatmentHistoryMetadata) {
        final String earTag = treatmentHistoryMetadata.getEarTag();
        final Optional<Cattle> existingCattle = cattleRepository.findByEarTag(earTag);
        Long cattleId = Long.valueOf(treatmentHistoryMetadata.getCattleId());

        if (cattleRepository.existsByCattleId(cattleId)) {
            throw new DuplicateCattleException("Cattle with ID " + cattleId + " already exists.");
        }

        if (existingCattle.isPresent()) {
            final Cattle cattle = existingCattle.get();
            cattle.setCattleId(cattleId);
            cattle.setIsInductionCompleted(true);
            cattle.setInductionDate(LocalDate.now());
            cattle.setUserId(treatmentHistoryMetadata.getUserId());
            cattleRepository.save(cattle);
        } else {
            throw new IllegalArgumentException("No Cattle record found with the given EarTag: " + earTag);
        }
    }


}
