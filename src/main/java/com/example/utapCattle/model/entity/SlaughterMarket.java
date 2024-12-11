package com.example.utapCattle.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "slaughtermarket", schema = "public")
public class SlaughterMarket {


    @Id
    @Column(name = "marketid")
    private Integer marketId;

    @Column(name = "marketname")
    private String marketName;

    @Column(name = "holdingnumber")
    private String holdingNumber;

    @Column(name = "current")
    private String current;
}
