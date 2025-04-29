package com.example.utapCattle.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "slaughterhouse")
public class SlaughterHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cattleid")
    private Long cattleId;

    @Column(name = "eartag1", length = 50)
    private String earTag1;

    @Column(name = "carcassnumber")
    private Integer carcassNumber;

    @Column(name = "side1weight", precision = 10, scale = 2)
    private BigDecimal side1Weight;

    @Column(name = "side2weight", precision = 10, scale = 2)
    private BigDecimal side2Weight;

    @Column(name = "hotweight", precision = 10, scale = 2)
    private BigDecimal hotWeight;

    @Column(name = "reb", precision = 10, scale = 2)
    private BigDecimal reb;

    @Column(name = "coldweight", precision = 10, scale = 2)
    private BigDecimal coldWeight;

    @Column(name = "kill_date")
    private LocalDate killDate;

    @Column(name = "farm_assured", length = 10)
    private String farmAssured;

    @Column(name = "grade", length = 10)
    private String grade;

    @Column(name = "category", length = 20)
    private String category;

    @Column(name = "breed", length = 50)
    private String breed;

    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @Column(name = "side1_label")
    private Integer side1Label;

    @Column(name = "side2_label")
    private Integer side2Label;

    @Column(name = "destination", length = 50)
    private String destination;

    @Column(name = "band", length = 20)
    private String band;

    @Column(name = "textbox50", precision = 10, scale = 2)
    private BigDecimal textBox50;

    @Column(name = "textbox51", precision = 10, scale = 2)
    private BigDecimal textBox51;

    @Column(name = "textbox52", precision = 10, scale = 2)
    private BigDecimal textBox52;

    @Column(name = "textbox53", precision = 10, scale = 2)
    private BigDecimal textBox53;

    @Column(name = "textbox54", precision = 10, scale = 2)
    private BigDecimal textBox54;

    @Column(name = "slaughter_market")
    private Long slaughterMarket;
}
