package com.example.utapCattle.adminactions.fatcover;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FatcoverService {

    @Autowired
    private FatcoverRepository repository;

    public List<Fatcover> getAll() {
        return repository.findAll();
    }

    public Optional<Fatcover> getById(Long id) {
        return repository.findById(id);
    }

    public Fatcover create(Fatcover condition) {
        return repository.save(condition);
    }

    public Fatcover update(Long id, Fatcover updatedCondition) {
        return repository.findById(id).map(condition -> {
            condition.setFatcoverDesc(updatedCondition.getFatcoverDesc());
            return repository.save(condition);
        }).orElseThrow(() -> new RuntimeException("Fat Cover not found"));
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Fat Cover not found");
        }
    }
}
