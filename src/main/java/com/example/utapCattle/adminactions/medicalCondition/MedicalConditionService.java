package com.example.utapCattle.adminactions.medicalCondition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.entity.MedicalCondition;
import com.example.utapCattle.service.repository.MedicalConditionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalConditionService {

    @Autowired
    private MedicalConditionRepository repository;

    public List<MedicalCondition> getAll(Long userFarmId) {
        return repository.findAllByUserFarmId(userFarmId);
    }

    public Optional<MedicalCondition> getById(Long id) {
        return repository.findById(id);
    }

    public MedicalCondition create(MedicalCondition condition) {
        return repository.save(condition);
    }

    public MedicalCondition update(Long id, MedicalCondition updatedCondition) {
        return repository.findById(id).map(condition -> {
            condition.setConditionDesc(updatedCondition.getConditionDesc());
            return repository.save(condition);
        }).orElseThrow(() -> new RuntimeException("Condition not found"));
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Condition not found");
        }
    }
}
