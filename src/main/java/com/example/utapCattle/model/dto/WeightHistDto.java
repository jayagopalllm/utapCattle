package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightHistDto {
    private String earTag;
    private Long cattleId;
    private String weightDateTime;
    private Double weight;
    private Double dlwgFarm;
}
