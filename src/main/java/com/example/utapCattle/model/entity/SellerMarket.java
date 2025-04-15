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
@Table(name = "sellermarket", schema = "public")
public class SellerMarket {

    @Id
    @Column(name = "sellermarketid")
    private Long sellerMarketId;

    @Column(name = "sellermarketname")
    private String sellerMarketName;

    @Column(name = "sellerholdingnumber")
    private String sellerHoldingNumber;

    @Column(name = "sellercurrent")
    private String sellerCurrent; // Consider using a boolean or enum if appropriate

    @Column(name = "userfarmid")
    private Long userFarmId;

}
