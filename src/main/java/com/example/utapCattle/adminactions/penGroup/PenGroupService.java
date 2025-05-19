package com.example.utapCattle.adminactions.penGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PenGroupService {

    @Autowired
    private PenGroupRepository repository;

    public List<PenGroup> getAll() {
        return repository.findAll();
    }

    public Optional<PenGroup> getById(Long id) {
        return repository.findById(id);
    }

    public PenGroup create(PenGroup condition) {
        return repository.save(condition);
    }

    public PenGroup update(Long id, PenGroup updatedCondition) {
        return repository.findById(id).map(condition -> {
            condition.setGroupDesc(updatedCondition.getGroupDesc());
            return repository.save(condition);
        }).orElseThrow(() -> new RuntimeException("Pen Group not found"));
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Pen Group not found");
        }
    }
}
