package com.example.utapCattle.carbon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalGroupDto {

    private String eartag;
    private Long cattleid;
    private String productionType;
    private String managementSystem;
    private String breedAbbr;
    private String sex;
    private Double lastWeight;
    private Double startWeight;
    private Double dlwgFarm;
    private Double startAgeMonths;
    private Long daysOnFarm;
    private String lifecycleStage;
    private String ageClass;


}
