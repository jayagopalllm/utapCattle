package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedDto {

    private Long breedid;
    private String breeddesc;
    private String breedabbr;
    private String breedfull;
    private String breedcatego;
    private String beefdairy;
    
}
