package com.example.utapCattle.adminactions.medication;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.adminactions.medicationSupplier.MedicationSupplierRepository;
import com.example.utapCattle.adminactions.medicationType.MedicationTypeRepository;
import com.example.utapCattle.model.entity.Medication;
import com.example.utapCattle.service.repository.MedicationRepository;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository repository;

    @Autowired
    private MedicationSupplierRepository supplierRepository;

    @Autowired
    private MedicationTypeRepository typeRepository;

    public List<Medication> getAll() {
        List<Medication> medications = repository.findAll();
        medications.forEach(this::populateTransientFields);
        return medications;
    }

    public Optional<Medication> getById(Long id) {
        return repository.findById(id).map(this::populateTransientFields);
    }

    public Medication create(Medication condition) {
        Medication saved = repository.save(condition);
        return populateTransientFields(saved);
    }

    public Medication update(Long id, Medication updatedCondition) {
        return repository.findById(id).map(condition -> {
            condition.setMedicationDesc(updatedCondition.getMedicationDesc());
            condition.setMedicationSupplierId(updatedCondition.getMedicationSupplierId());
            condition.setWithdrawalPeriod(updatedCondition.getWithdrawalPeriod());
            condition.setMedicationTypeId(updatedCondition.getMedicationTypeId());
            condition.setBatchNumber(updatedCondition.getBatchNumber());
            Medication updated = repository.save(condition);
            return populateTransientFields(updated);
        }).orElseThrow(() -> new RuntimeException("Medication not found"));
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Medication not found");
        }
    }

    private Medication populateTransientFields(Medication medication) {
        supplierRepository.findById(medication.getMedicationSupplierId())
                .ifPresent(supplier -> medication.setSupplierName(supplier.getSupplierName()));

        typeRepository.findById(medication.getMedicationTypeId())
                .ifPresent(type -> medication.setMedicationTypeDesc(type.getMedicationTypeDesc()));

        return medication;
    }
}
