package com.example.utapCattle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "filtercriteria", schema = "public")
public class FilterCriteria {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "filtername")
    private String filterName;

    @Column(name = "filterflag")
    private String filterFlag;

    @Column(name = "filterby")
    private String filterBy;

    @Column(name = "values")
    private String value;

}
