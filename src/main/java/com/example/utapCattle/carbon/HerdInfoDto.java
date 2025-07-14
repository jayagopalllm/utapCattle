package com.example.utapCattle.carbon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HerdInfoDto {

    private Integer totalCount;
    private Double avgWeight;
    private Double avgDlwgFarm;

}
