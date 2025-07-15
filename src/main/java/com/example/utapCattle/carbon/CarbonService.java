package com.example.utapCattle.carbon;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CarbonService {

    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public CarbonService(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public List<AnimalGroupDto> getAnimalGroupInfo(LocalDate startDate, LocalDate endDate, String holdingNo) {

        String sql = """
                    With
                    weightranked as (
                        select
                            w.cattleid,w.weight,
                            w.weightdatetime::date as date_weighted,
                            ROW_NUMBER() OVER (PARTITION BY w.cattleid ORDER BY w.weightdatetime::timestamp ) AS min_rank,
                            ROW_NUMBER() OVER (PARTITION BY w.cattleid ORDER BY w.weightdatetime::timestamp DESC) AS max_rank
                        from
                            weighthistory w
                        where
                            w.weight>25 and
                        w.weightdatetime::date between (:startDate - interval '3 months') and (:endDate + interval '3 months')
                        ),
                    cattle_present as (
                        select
                            c.eartag,
                            c.cattleid,
                            uf.production_type,
                            uf.management_system,
                            b.breedabbr as breed_abbr,
                            c2.sex,
                            c.dateofbirth::date,
                            c.datepurchased::date,
                            greatest(c.datepurchased::date, :startDate) as start_date,
                            least(
                            s.saledate::date,
                            case when c.health_status = 'DIED' then coalesce(s.saledate::date, dr.death_date) else null end,
                            :endDate
                        ) as end_date,
                            s.saledate::date,
                            w_max.weight as last_weight,
                            w_min.weight as start_weight,
                            (w_max.weight - w_min.weight) / nullif(w_max.date_weighted::date - w_min.date_weighted::date, 0) as dlwg_farm,
                            ( greatest(c.datepurchased::date, :startDate) - c.dateofbirth::date) / 30.4375 as start_age_months,
                            c.ownerfarmid
                        from
                            cattle c
                            left outer join sale s on s.saleid = c.saleid
                            left outer join death_record dr on c.eartag = dr.eartag
                            left join weightranked w_min on c.cattleid = w_min.cattleid and w_min.min_rank = 1
                            left join weightranked w_max on c.cattleid = w_max.cattleid and w_max.max_rank = 1
                            left join breed b on b.breedid = c.breedid
                            left join category c2 on c2.categoryid = c.categoryid
                            inner join user_farm uf on uf.id = c.ownerfarmid
                        where uf.cph_number = :holdingNo
                        )

                    select
                        cp.eartag,
                        cp.cattleid,
                        cp.production_type,
                        cp.management_system,
                        cp.breed_abbr,
                        cp.sex,
                        cp.last_weight,
                        cp.start_weight,
                        round(cp.dlwg_farm::numeric,2) as dlwg_farm,
                        round(cp.start_age_months::numeric,2) as start_age_months,
                        cp.end_date-cp.start_date as days_on_farm,
                        case
                            when start_age_months <= 2 then 'Birth'
                            when start_age_months > 2 and start_age_months <= 6 then 'Weaning'
                            when start_age_months > 6 and start_age_months <= 24 then 'Finishing'
                            when start_age_months > 24 then 'Culling/Slaughter'
                        end as lifecycle_stage,
                        CASE
                            WHEN start_age_months <= 6 THEN 'Calf'
                            WHEN sex = 'F' AND start_age_months > 6 AND start_age_months <= 24 THEN 'Heifer'
                            WHEN sex = 'F' AND start_age_months > 24 THEN 'Mature Cow'
                            WHEN sex = 'M' AND start_age_months > 6 THEN 'Finishing Cattle'
                        END AS age_class
                    from
                        cattle_present cp
                    where
                        cp.start_date <= cp.end_date
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("startDate", startDate)
                .addValue("endDate", endDate)
                .addValue("holdingNo", holdingNo);

        return namedJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            AnimalGroupDto dto = new AnimalGroupDto();
            dto.setEartag(rs.getString("eartag"));
            dto.setCattleid(rs.getLong("cattleid"));
            dto.setProductionType(rs.getString("production_type"));
            dto.setManagementSystem(rs.getString("management_system"));
            dto.setBreedAbbr(rs.getString("breed_abbr"));
            dto.setSex(rs.getString("sex"));
            dto.setLastWeight(rs.getDouble("last_weight"));
            dto.setStartWeight(rs.getDouble("start_weight"));
            dto.setDlwgFarm(rs.getDouble("dlwg_farm"));
            dto.setStartAgeMonths(rs.getDouble("start_age_months"));
            dto.setDaysOnFarm(rs.getLong("days_on_farm"));
            dto.setLifecycleStage(rs.getString("lifecycle_stage"));
            dto.setAgeClass(rs.getString("age_class"));
            return dto;
        });
    }

    public HerdInfoDto getHerdInfo(LocalDate startDate, LocalDate endDate, String holdingNo) {

        String sql = """
                with
                weightranked as (
                	select
                		w.cattleid,w.weight,
                		w.weightdatetime::date as date_weighted,
                		ROW_NUMBER() OVER (PARTITION BY w.cattleid ORDER BY w.weightdatetime::timestamp ) AS min_rank,
                	    ROW_NUMBER() OVER (PARTITION BY w.cattleid ORDER BY w.weightdatetime::timestamp DESC) AS max_rank
                	from
                		weighthistory w
                	where
                		w.weight>25 and
                	  w.weightdatetime::date between (:startDate - interval '3 months') and (:endDate + interval '3 months')
                	),
                cattle_present as (
                	select
                		c.eartag,
                		greatest(c.datepurchased::date, :startDate) as start_date,
                		least(
                	    s.saledate::date,
                	    case when c.health_status = 'DIED' then coalesce(s.saledate::date, dr.death_date) else null end,
                	    :endDate
                	  ) as end_date,
                		s.saledate::date,
                		w_max.weight as last_weight,
                		w_min.weight as start_weight,
                		(w_max.weight - w_min.weight) / nullif(w_max.date_weighted::date - w_min.date_weighted::date, 0) as dlwg_farm,
                		( greatest(c.datepurchased::date, :startDate) - c.dateofbirth::date) / 30.4375 as start_age_months,
                		c.ownerfarmid
                	from
                		cattle c
                		left outer join sale s on s.saleid = c.saleid
                		left outer join death_record dr on c.eartag = dr.eartag
                		left join weightranked w_min on c.cattleid = w_min.cattleid and w_min.min_rank = 1
                		left join weightranked w_max on c.cattleid = w_max.cattleid and w_max.max_rank = 1
                		inner join user_farm uf on uf.id = c.ownerfarmid
                	where uf.cph_number = :holdingNo
                	)

                select
                	count(cp.eartag) as total_count,
                	ROUND(AVG(cp.last_weight - cp.start_weight)::numeric, 2) AS avg_weight,
                	ROUND(AVG(cp.dlwg_farm::numeric), 2) AS avg_dlwg_farm
                from
                	cattle_present cp
                where
                	cp.start_date <= cp.end_date
                                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("startDate", startDate)
                .addValue("endDate", endDate)
                .addValue("holdingNo", holdingNo);

        HerdInfoDto herdInfo = namedJdbcTemplate.queryForObject(sql, params,
            (rs, rowNum) -> {
                HerdInfoDto dto = new HerdInfoDto();
                dto.setTotalCount(rs.getInt("total_count"));
                dto.setAvgWeight(rs.getDouble("avg_weight"));
                dto.setAvgDlwgFarm(rs.getDouble("avg_dlwg_farm"));
                return dto;
            });
        return herdInfo;
    }

    public Map<String, Map<String, Double>> getFeedInfo(LocalDate startDate, LocalDate endDate, String holdingNo) {

        String sql = """
                SELECT
                    feed_state,
                    ingredient,
                    ROUND(SUM(quantity)::numeric, 2) AS total_quantity
                FROM
                    farm_feed_daily ffd
                    inner join user_farm uf on uf.id = ffd.user_farmid
                WHERE
                    feed_date BETWEEN :startDate AND :endDate
                    and uf.cph_number = :holdingNo
                GROUP BY
                    feed_state,ingredient
                ORDER BY
                    feed_state;
                                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("startDate", startDate)
                .addValue("endDate", endDate)
                .addValue("holdingNo", holdingNo);

        Map<String, Map<String, Double>> result = new HashMap<>();

        namedJdbcTemplate.query(sql, params, rs -> {
            String feedState = rs.getString("feed_state");
            String ingredient = rs.getString("ingredient");
            Double totalQuantity = rs.getDouble("total_quantity");

            result.computeIfAbsent(feedState, k -> new HashMap<>())
                  .put(ingredient, totalQuantity);
        });

        return result;

    }

}
