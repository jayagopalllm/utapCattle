package com.example.utapCattle.carbon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalGroupDto {

    private Integer cattleCount;
    private String productionType;
    private String managementSystem;
    private Double lastWeight;
    private Double startWeight;
    private Double dlwgFarm;
    private Long startAgeMonths;
    private Long daysOnFarm;
    private String animalGroup;


}
