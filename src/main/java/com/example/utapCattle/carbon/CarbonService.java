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
                                      WITH
                      weightranked AS (
                          -- same as your original definition
                          SELECT
                              w.cattleid, w.weight,
                              w.weightdatetime::date AS date_weighted,
                              ROW_NUMBER() OVER (PARTITION BY w.cattleid ORDER BY w.weightdatetime) AS min_rank,
                              ROW_NUMBER() OVER (PARTITION BY w.cattleid ORDER BY w.weightdatetime DESC) AS max_rank
                          FROM
                              weighthistory w
                          WHERE
                              w.weight > 25
                              AND w.weightdatetime::date BETWEEN (:startDate - interval '3 months') AND (:endDate + interval '3 months')
                      ),
                      cattle_present AS (
                          -- your cattle-present logic
                          SELECT
                              c.eartag,
                              c.cattleid,
                              uf.production_type,
                              uf.management_system,
                              b.breedabbr AS breed_abbr,
                              c2.categorydesc AS categorydesc,
                              c2.sex,
                              c.dateofbirth::date,
                              c.datepurchased::date,
                              GREATEST(c.datepurchased::date, :startDate) AS start_date,
                              LEAST(
                                  s.saledate::date,
                                  CASE WHEN c.health_status = 'DIED' THEN COALESCE(s.saledate::date, dr.death_date) ELSE NULL END,
                                  :endDate
                              ) AS end_date,
                              s.saledate::date,
                              w_max.weight AS last_weight,
                              w_min.weight AS start_weight,
                              (w_max.weight - w_min.weight) / NULLIF(w_max.date_weighted::date - w_min.date_weighted::date, 0) AS dlwg_farm,
                              ROUND((GREATEST(c.datepurchased::date, :startDate) - c.dateofbirth::date) / 30.4375) AS start_age_months,
                              c.ownerfarmid
                          FROM
                              cattle c
                              LEFT JOIN sale s ON s.saleid = c.saleid
                              LEFT JOIN death_record dr ON c.eartag = dr.eartag
                              LEFT JOIN weightranked w_min ON c.cattleid = w_min.cattleid AND w_min.min_rank = 1
                              LEFT JOIN weightranked w_max ON c.cattleid = w_max.cattleid AND w_max.max_rank = 1
                              LEFT JOIN breed b ON b.breedid = c.breedid
                              LEFT JOIN category c2 ON c2.categoryid = c.categoryid
                              INNER JOIN user_farm uf ON uf.id = c.ownerfarmid
                          WHERE
                              uf.cph_number = :holdingNo and c.dateofbirth::date > '2010-12-31'
                      ),
                      classified AS (
                          -- apply classification
                          SELECT
                              cp.*,
                              ROUND(cp.dlwg_farm::numeric, 2) AS dlwg_farm_rounded,
                              cp.end_date - cp.start_date AS days_on_farm,
                              CASE
                                  WHEN LOWER(TRIM(categorydesc)) = 'heifer' THEN
                                      CASE
                                          WHEN start_age_months BETWEEN 0 AND 11 THEN 'Heifer 0-12 mnth'
                                          WHEN start_age_months BETWEEN 12 AND 23 THEN 'Heifer 12-24 mnth'
                                          WHEN start_age_months BETWEEN 24 AND 36 THEN 'Heifer 24-36 mnth'
                                          ELSE 'Suckler cow'
                                      END
                                  WHEN LOWER(TRIM(categorydesc)) = 'steer' THEN
                                      CASE
                                          WHEN start_age_months BETWEEN 0 AND 11 THEN 'Steer 0-12 mnth'
                                          WHEN start_age_months BETWEEN 12 AND 23 THEN 'Steer 12-24 mnth'
                                          ELSE 'Steer 24-36 mnth'
                                      END
                                  WHEN LOWER(TRIM(categorydesc)) = 'male entire' THEN
                                      CASE
                                          WHEN start_age_months BETWEEN 0 AND 11 THEN 'Male entire 0-12 mnth'
                                          ELSE 'Male entire 12-23 mnth'
                                      END
                                  WHEN LOWER(TRIM(categorydesc)) = 'cow' THEN 'Suckler cow'
                                  WHEN LOWER(TRIM(categorydesc)) IN ('bull calf', 'grazing bull') THEN
                                      CASE
                                          WHEN start_age_months <= 23 THEN 'Male entire 12-23 mnth'
                                          ELSE 'Bull'
                                      END
                                  WHEN TRIM(categorydesc) = 'F' THEN
                                      CASE
                                          WHEN start_age_months BETWEEN 24 AND 36 THEN 'Heifer 24-36 mnth'
                                          WHEN start_age_months BETWEEN 12 AND 23 THEN 'Heifer 12-24 mnth'
                                          WHEN start_age_months BETWEEN 0 AND 11 THEN 'Heifer 0-12 mnth'
                                          ELSE 'Suckler cow'
                                      END
                                  WHEN TRIM(categorydesc) = 'M' THEN
                                      CASE
                                          WHEN start_age_months BETWEEN 24 AND 36 THEN 'Steer 24-36 mnth'
                                          WHEN start_age_months BETWEEN 12 AND 23 THEN 'Male entire 12-23 mnth'
                                          WHEN start_age_months BETWEEN 0 AND 11 THEN 'Male entire 0-12 mnth'
                                          ELSE  'Bull'
                                      END
                                  ELSE 'Unclassified'
                              END AS animal_group
                          FROM
                              cattle_present cp
                          WHERE
                              cp.start_date <= cp.end_date
                      )
                  -- Final aggregation
                  SELECT
                      animal_group,
                      production_type,
                      management_system,
                      COUNT(*) AS cattle_count,
                      ROUND(AVG(last_weight)::numeric, 2) AS avg_last_weight,
                      ROUND(AVG(start_weight)::numeric, 2) AS avg_start_weight,
                      ROUND(AVG(dlwg_farm_rounded)::numeric, 2) AS avg_dlwg_farm,
                      ROUND(AVG(start_age_months)::numeric,0) AS avg_start_age_months,
                      ROUND(AVG(days_on_farm)::numeric, 0) AS avg_days_on_farm
                  FROM
                      classified
                  GROUP BY
                      animal_group, production_type,management_system
                  ORDER BY
                      animal_group
                                  """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("startDate", startDate)
                .addValue("endDate", endDate)
                .addValue("holdingNo", holdingNo);

        return namedJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            AnimalGroupDto dto = new AnimalGroupDto();
            dto.setAnimalGroup(rs.getString("animal_group"));
            dto.setProductionType(rs.getString("production_type"));
            dto.setManagementSystem(rs.getString("management_system"));
            dto.setCattleCount(rs.getInt("cattle_count"));
            dto.setLastWeight(rs.getDouble("avg_last_weight"));
            dto.setStartWeight(rs.getDouble("avg_start_weight"));
            dto.setDlwgFarm(rs.getDouble("avg_dlwg_farm"));
            dto.setStartAgeMonths(rs.getLong("avg_start_age_months"));
            dto.setDaysOnFarm(rs.getLong("avg_days_on_farm"));
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
