package com.example.utapCattle.adminactions.medicationType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationTypeService {

    @Autowired
    private MedicationTypeRepository repository;

    public List<MedicationType> getAll(Long userFarmId) {
        return repository.findByUserFarmId(userFarmId);
    }

    public Optional<MedicationType> getById(Long id) {
        return repository.findById(id);
    }

    public MedicationType create(MedicationType condition) {
        return repository.save(condition);
    }

    public MedicationType update(Long id, MedicationType updatedCondition) {
        return repository.findById(id).map(condition -> {
            condition.setMedicationTypeDesc(updatedCondition.getMedicationTypeDesc());
            return repository.save(condition);
        }).orElseThrow(() -> new RuntimeException("Medication Type not found"));
    }
}
