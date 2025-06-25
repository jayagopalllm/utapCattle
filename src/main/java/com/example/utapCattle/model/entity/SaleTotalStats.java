package com.example.utapCattle.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleTotalStats {
    private Integer totalCattle;

    private Integer totalWeight;

    private Integer totalOTM;

    private Double avgWeight;

    private Double avgDlwg;
}
