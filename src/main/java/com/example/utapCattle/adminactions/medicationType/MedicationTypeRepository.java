package com.example.utapCattle.adminactions.medicationType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationTypeRepository extends JpaRepository<MedicationType, Long> {

}
