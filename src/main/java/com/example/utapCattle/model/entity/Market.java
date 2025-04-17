package com.example.utapCattle.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "market", schema = "public")
public class Market {

    @Id
    @Column(name = "marketid")
    private Integer marketId;

    @Column(name = "marketname")
    private String marketName;

    @Column(name = "holdingnumber")
    private String holdingNumber;

    @Column(name = "current")
    private String current; // Consider using a boolean or enum if appropriate

    @Column(name = "userfarmid")
    private Long userFarmId;

}
