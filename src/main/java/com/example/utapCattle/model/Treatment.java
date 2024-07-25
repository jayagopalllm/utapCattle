package com.example.utapCattle.model;

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
@Table(name = "TREATMENT", schema = "cattleco")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicationid")
    private Long medicationid;

    @Column(name = "medicationdesc", nullable = false)
    private String medicationdesc;

    @Column(name = "medicationsupplierid", nullable = false)
    private int medicationsupplierid;

    @Column(name = "withdrawalperiod", nullable = false)
    private int withdrawalperiod;

    @Column(name = "medicationtypeid", nullable = false)
    private int medicationtypeid;

    @Column(name = "batchnumber", nullable = false)
    private String batchnumber;
    
}
