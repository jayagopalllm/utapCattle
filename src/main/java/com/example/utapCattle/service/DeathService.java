package com.example.utapCattle.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.DeathRecord;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.DeathRepository;
import com.example.utapCattle.utils.DateUtils;

import org.springframework.transaction.annotation.Transactional;

@Service
public class DeathService {

    private final CattleRepository cattleRepository;
    private final DeathRepository deathRepository;

    public DeathService(CattleRepository cattleRepository, DeathRepository deathRepository) {
        this.cattleRepository = cattleRepository;
        this.deathRepository = deathRepository;
    }

    public Map<String, Object> getOnloadData(Long userFarmId) {
        return Map.of(
                "earTagList", cattleRepository.getOnFarmCattleEarTags(userFarmId));
    }

    @Transactional
    public List<String> save(DeathRecord deathRecord) {

        List<String> earTagList = deathRecord.getEarTags();

        if (earTagList == null || earTagList.isEmpty()) {
            throw new IllegalArgumentException("Ear tag list cannot be empty");
        }

        List<String> savedRecords = new ArrayList<>();

        for (String earTag : earTagList) {
            Optional<Cattle> cattleOptional = cattleRepository.findByEarTag(earTag);

            if (cattleOptional.isPresent()) {
                Cattle cattle = cattleOptional.get();

                // Update health status
                cattle.setHealthStatus("DIED");
                cattle.setSaleId(9999999L);
                cattleRepository.save(cattle);

                DeathRecord recordToSave = deathRepository.findByEarTag(earTag)
                        .map(existingRecord -> {
                            existingRecord.setDeathDate(deathRecord.getDeathDate());
                            existingRecord.setDeathReason(deathRecord.getDeathReason());
                            existingRecord.setComments(deathRecord.getComments());
                            existingRecord.setAbattoir(deathRecord.getAbattoir());
                            existingRecord.setOtherLocation(deathRecord.getOtherLocation());
                            return existingRecord;
                        })
                        .orElseGet(() -> {
                            DeathRecord newRecord = new DeathRecord();
                            newRecord.setEarTag(earTag);
                            newRecord.setDeathDate(deathRecord.getDeathDate());
                            newRecord.setDeathReason(deathRecord.getDeathReason());
                            newRecord.setComments(deathRecord.getComments());
                            newRecord.setAbattoir(deathRecord.getAbattoir());
                            newRecord.setOtherLocation(deathRecord.getOtherLocation());
                            return newRecord;
                        });

                DeathRecord savedRecord = deathRepository.save(recordToSave);
                savedRecords.add(savedRecord.getEarTag());

            } else {
                throw new IllegalArgumentException("Cattle not found with earTag: " + earTag);
            }
        }

        return savedRecords;
    }

}
