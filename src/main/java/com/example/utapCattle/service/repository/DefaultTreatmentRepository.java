package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.example.utapCattle.model.dto.DefaultTreatmentDto;
import com.example.utapCattle.model.entity.DefaultTreatment;

@Repository
public interface DefaultTreatmentRepository extends JpaRepository<DefaultTreatment, Long> {

    @Query(value = "SELECT nextval('compulsorytreatment_seq')", nativeQuery = true)
    Long getNextSequenceValue();

    @Query(value = """
    SELECT compulsorytreatmentid, description,medicalconditionid,medicationid, medical_conditiondesc, medicationdesc, batchnumber, userfarmid
    FROM vw_default_treatments
    WHERE userfarmid = :userFarmId
    """, nativeQuery = true)
    List<Object[]> findAllWithBatchNumber(@Param("userFarmId") Long userFarmId);



}
