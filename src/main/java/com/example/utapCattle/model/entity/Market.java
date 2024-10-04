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
@Table(name = "market", schema = "public")
public class Market {

    @Id
    @Column(name = "marketid")
    private Long marketId;

    @Column(name = "marketname")
    private String marketName;

    @Column(name = "holdingnumber")
    private String holdingNumber;

    @Column(name = "current")
    private String current; // Consider using a boolean or enum if appropriate

}
