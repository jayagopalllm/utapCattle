package com.example.utapCattle.adminactions.pen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.adminactions.penGroup.PenGroupRepository;
import com.example.utapCattle.model.entity.Pen;
import com.example.utapCattle.service.repository.PenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PenService {

    @Autowired
    private PenRepository repository;

    @Autowired
    private PenGroupRepository penGroupRepository;

    public List<Pen> getAll(Long userFarmId) {
        List<Pen> pens = repository.findAllByUserFarmId(userFarmId);
        pens.forEach(this::populateTransientFields);
        return pens;
    }

    public Optional<Pen> getById(Long id) {
        return repository.findById(id);
    }

    public Pen create(Pen condition) {
        return repository.save(condition);
    }

    public Pen update(Long id, Pen updatedCondition) {
        return repository.findById(id).map(condition -> {
            condition.setPenName(updatedCondition.getPenName());
            condition.setPenGroupId(updatedCondition.getPenGroupId());
            return repository.save(condition);
        }).orElseThrow(() -> new RuntimeException("Pen not found"));
    }

    private Pen populateTransientFields(Pen pen) {
        penGroupRepository.findById(pen.getPenGroupId())
                .ifPresent(supplier -> pen.setGroupDesc(supplier.getGroupDesc()));

        return pen;
    }
}
