package com.example.utapCattle.redtractor.treatment;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class RedTractorTreatmentService {

    private final JdbcTemplate jdbcTemplate;

    public RedTractorTreatmentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RedTractorTreatmentDto> getAllTreatmentList(Long userFarmId) {
        String sql = """
                select TO_CHAR(t.treatmentdate::date, 'DD/MM/YYYY') as treatmentdate,c.eartag,m.medicationdesc,t.batchnumber,m.withdrawalperiod ,TO_CHAR(t.withdrawaldate::date, 'DD/MM/YYYY') as withdrawaldate,m2.conditiondesc ,u.username
                from treatmenthistory t
                	inner join cattle c on  c.cattleid = t.cattleid
                	inner join  medication m on m.medicationid =t.medicationid
                	inner join medicalcondition m2 on m2.medicalconditionid = t.medicalconditionid
                	left join users u on u.id =t.userid
                where TO_DATE(c.datepurchased, 'YYYY-MM-DD') BETWEEN CURRENT_DATE - INTERVAL '12 months' AND CURRENT_DATE
                     and c.ownerfarmid = ?
                     order by t.treatmenthistoryid desc
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RedTractorTreatmentDto dto = new RedTractorTreatmentDto();
            dto.setTreatmentDate(rs.getString("treatmentdate"));
            dto.setEarNumber(rs.getString("eartag"));
            dto.setMedication(rs.getString("medicationdesc"));
            dto.setBatchNumber(rs.getString("batchnumber"));
            dto.setWithdrawalPeriod(rs.getString("withdrawalperiod"));
            dto.setWithdrawalDate(rs.getString("withdrawaldate"));
            dto.setMedicalCondition(rs.getString("conditiondesc"));
            dto.setAdministeredBy(rs.getString("username"));
            return dto;
        }, userFarmId);
    }

    public List<RedTractorWithdrawalSaleDto>  getWithdrawalPeriodSale(Long userFarmId) {
        String sql = """
                WITH
                    latest_treatment AS (
                        SELECT th.cattleid, th.medicationid, th.treatmentdate, th.withdrawaldate,
                            ROW_NUMBER() OVER (PARTITION BY th.cattleid ORDER BY th.withdrawaldate::date DESC) as rn
                        FROM cattle c
                        JOIN sale s ON c.saleid = s.saleid
                        JOIN treatmenthistory th ON c.cattleid = th.cattleid
                        WHERE th.withdrawaldate::date >= s.saledate::date
                            and TO_DATE(c.datepurchased, 'YYYY-MM-DD') BETWEEN CURRENT_DATE - INTERVAL '12 months' AND CURRENT_DATE
                    ),
                    medication_details AS (
                        SELECT lt.cattleid, lt.treatmentdate, lt.withdrawaldate,
                            m.medicationdesc, m.withdrawalperiod
                        FROM latest_treatment lt
                        JOIN medication m ON lt.medicationid = m.medicationid
                        WHERE lt.rn = 1
                    ),
                    aggregated_meds AS (
                        SELECT th.cattleid, STRING_AGG(DISTINCT m.medicationdesc, ', ' ORDER BY m.medicationdesc) as medications
                        FROM treatmenthistory th
                        JOIN medication m ON th.medicationid = m.medicationid
                        JOIN cattle c ON th.cattleid = c.cattleid
                        JOIN sale s ON c.saleid = s.saleid
                        WHERE th.withdrawaldate::date >= s.saledate::date
                            and TO_DATE(c.datepurchased, 'YYYY-MM-DD') BETWEEN CURRENT_DATE - INTERVAL '12 months' AND CURRENT_DATE
                        GROUP BY th.cattleid
                    )
                SELECT c.eartag,b.breedabbr, TO_CHAR(s.saledate::date, 'DD/MM/YYYY') as saledate, am.medications,
                	TO_CHAR(md.withdrawaldate::date, 'DD/MM/YYYY') as withdrawaldate,
                    TO_CHAR(md.treatmentdate::date, 'DD/MM/YYYY') as treatmentdate,
                    md.withdrawalperiod
                FROM cattle c
                JOIN sale s ON c.saleid = s.saleid
                join market m on m.marketid = s.salemarketid
                join breed b on b.breedid=c.breedid
                JOIN medication_details md ON c.cattleid = md.cattleid
                JOIN aggregated_meds am ON c.cattleid = am.cattleid
                where TO_DATE(c.datepurchased, 'YYYY-MM-DD') BETWEEN CURRENT_DATE - INTERVAL '12 months' AND CURRENT_DATE
                	and c.ownerfarmid = ?
                	--and m.marketname !='DIED'
                ORDER BY s.saledate::date desc;

                                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RedTractorWithdrawalSaleDto dto = new RedTractorWithdrawalSaleDto();
            dto.setEarNumber(rs.getString("eartag"));
            dto.setBreed(rs.getString("breedabbr"));
            dto.setMedication(rs.getString("medications"));
            dto.setTreatmentDate(rs.getString("treatmentdate"));
            dto.setWithdrawalPeriod(rs.getString("withdrawalperiod"));
            dto.setWithdrawalDate(rs.getString("withdrawaldate"));
            dto.setSoldOn(rs.getString("saledate"));
            return dto;
        }, userFarmId);
    }

}
