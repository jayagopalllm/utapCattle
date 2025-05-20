package com.example.utapCattle.adminactions.medicationSupplier;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicationSupplierRepository extends JpaRepository<MedicationSupplier, Long> {

    List<MedicationSupplier> findByUserFarmId(Long userFarmId);

}
