package com.example.utapCattle.redtractor.movement;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedTractorMovementService {
    private final JdbcTemplate jdbcTemplate;

    public RedTractorMovementService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RedTractorMovementDto> getAllMovementList(Long userFarmId) {
        String sql = """
                with
                	Cattle_Died as
                	(
                	select
                		cattleid,s.saledate
                	from cattle c
                		inner join sale s on s.saleid=c.saleid
                		inner join market m on m.marketid = s.salemarketid
                	where marketname = 'DIED'
                		and TO_DATE(c.datepurchased, 'YYYY-MM-DD') BETWEEN CURRENT_DATE - INTERVAL '12 months' AND CURRENT_DATE
                	)
                select c.eartag,TO_CHAR(c.dateofbirth::date, 'DD/MM/YYYY') as dateofbirth,TO_CHAR(c.datepurchased::date, 'DD/MM/YYYY') as datepurchased,b.breedabbr,c2.sex,f.farmname,TO_CHAR(s.saledate::date, 'DD/MM/YYYY') as saledate
                from cattle c
                	inner join breed b on b.breedid =c.breedid
                	inner join category c2 on c2.categoryid = c.categoryid
                	inner join farm f on f.farmid = c.farmid
                	left join sale s on s.saleid = c.saleid
                    left join Cattle_Died cd on cd.cattleid  = c.cattleid
                where TO_DATE(c.datepurchased, 'YYYY-MM-DD') BETWEEN CURRENT_DATE - INTERVAL '12 months' AND CURRENT_DATE
                	and cd.cattleid is null and c.ownerfarmid = ?
                    order by c.datepurchased::date desc
                                                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RedTractorMovementDto dto = new RedTractorMovementDto();
            dto.setEarNumber(rs.getString("eartag"));
            dto.setDateOfBirth(rs.getString("dateofbirth"));
            dto.setDateIn(rs.getString("datepurchased"));
            dto.setBreed(rs.getString("breedabbr"));
            dto.setSex(rs.getString("sex"));
            dto.setBought(rs.getString("farmname"));
            dto.setSoldOn(rs.getString("saledate"));
            return dto;
        }, userFarmId);
    }

    public List<RedTractorMovementDto> getDeathRecords(Long userFarmId) {
        String sql = """
                with
                	Cattle_Died as
                	(
                	select
                		cattleid,s.saledate
                	from cattle c
                		inner join sale s on s.saleid=c.saleid
                		inner join market m on m.marketid = s.salemarketid
                	where marketname = 'DIED'
                		and TO_DATE(c.datepurchased, 'YYYY-MM-DD') BETWEEN CURRENT_DATE - INTERVAL '12 months' AND CURRENT_DATE
                	)

                select c.eartag,TO_CHAR(c.dateofbirth::date, 'DD/MM/YYYY') as dateofbirth,TO_CHAR(c.datepurchased::date, 'DD/MM/YYYY') as datepurchased,b.breedabbr,c2.sex,f.farmname,TO_CHAR(cd.saledate::date, 'DD/MM/YYYY') as saledate
                from cattle c
                	inner join breed b on b.breedid =c.breedid
                	inner join category c2 on c2.categoryid = c.categoryid
                	inner join farm f on f.farmid = c.farmid
                	inner join Cattle_Died cd on cd.cattleid  = c.cattleid
                where TO_DATE(c.datepurchased, 'YYYY-MM-DD') BETWEEN CURRENT_DATE - INTERVAL '12 months' AND CURRENT_DATE
                	and c.ownerfarmid = ?
                    order by cd.saledate::date desc
                                                """;
        List<RedTractorMovementDto> dtoList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            RedTractorMovementDto dto = new RedTractorMovementDto();
            dto.setEarNumber(rs.getString("eartag"));
            dto.setDateOfBirth(rs.getString("dateofbirth"));
            dto.setDateIn(rs.getString("datepurchased"));
            dto.setBreed(rs.getString("breedabbr"));
            dto.setSex(rs.getString("sex"));
            dto.setBought(rs.getString("farmname"));
            dto.setDiedOn(rs.getString("saledate"));
            return dto;
        }, userFarmId);

        return dtoList;
    }

}
