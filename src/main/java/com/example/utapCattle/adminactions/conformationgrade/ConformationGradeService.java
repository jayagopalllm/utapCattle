package com.example.utapCattle.adminactions.conformationgrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConformationGradeService {

    @Autowired
    private ConformationGradeRepository repository;

    public List<ConformationGrade> getAll() {
        return repository.findAll();
    }

    public Optional<ConformationGrade> getById(Long id) {
        return repository.findById(id);
    }

    public ConformationGrade create(ConformationGrade condition) {
        return repository.save(condition);
    }

    public ConformationGrade update(Long id, ConformationGrade updatedCondition) {
        return repository.findById(id).map(condition -> {
            condition.setConformationDesc(updatedCondition.getConformationDesc());
            return repository.save(condition);
        }).orElseThrow(() -> new RuntimeException("Conformation Grade not found"));
    }
}
