package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterCriteriaDto {

    private Long id;
    private String filterName;
    private String filterFlag;
    private String filterBy;
    private String value;
}
