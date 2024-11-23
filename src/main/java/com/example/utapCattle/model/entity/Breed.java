package com.example.utapCattle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "breed", schema = "public")
public class Breed {

    @Id // This designates 'breedid' as the primary key 
    @Column(name = "breedid")
    private Long breedid;

    @Column(name = "breeddesc")
    private String breeddesc;

    @Column(name = "breedabbr")
    private String breedabbr;

    @Column(name = "breedfull")
    private String breedfull;

    @Column(name = "breedcategory")
    private String breedcatego;

    @Column(name = "beefdairy")
    private String beefdairy;

    @Override
    public String toString() {
        return "Breed{" +
                "breedid=" + breedid +
                ", breeddesc='" + breeddesc + '\'' +
                ", breedabbr='" + breedabbr + '\'' +
                ", breedfull='" + breedfull + '\'' +
                ", breedcatego='" + breedcatego + '\'' +
                ", beefdairy='" + beefdairy + '\'' +
                '}';
    }
}

