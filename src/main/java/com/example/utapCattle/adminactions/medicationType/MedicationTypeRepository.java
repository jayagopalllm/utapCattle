package com.example.utapCattle.adminactions.medicationType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicationTypeRepository extends JpaRepository<MedicationType, Long> {

    List<MedicationType> findByUserFarmId(Long userFarmId);

}
