package com.example.utapCattle.adminactions.medicationSupplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationSupplierService {

    @Autowired
    private MedicationSupplierRepository repository;

    public List<MedicationSupplier> getAll(Long userFarmId) {
        return repository.findByUserFarmIdOrderBySupplierNameAsc(userFarmId);
    }

    public Optional<MedicationSupplier> getById(Long id) {
        return repository.findById(id);
    }

    public MedicationSupplier create(MedicationSupplier condition) {
        return repository.save(condition);
    }

    public MedicationSupplier update(Long id, MedicationSupplier updatedCondition) {
        return repository.findById(id).map(condition -> {
            condition.setSupplierName(updatedCondition.getSupplierName());
            return repository.save(condition);
        }).orElseThrow(() -> new RuntimeException("Medication Supplier not found"));
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Medication Supplier not found");
        }
    }
}
