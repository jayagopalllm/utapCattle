package com.example.utapCattle.adminactions.medicationSupplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationSupplierRepository extends JpaRepository<MedicationSupplier, Long> {

}
