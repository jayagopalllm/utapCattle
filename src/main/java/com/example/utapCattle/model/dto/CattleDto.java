package com.example.utapCattle.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CattleDto {
    private Long cattleId;
    private String earTag;
    private Integer tagId;
    private String newTagReqd;
    private String dateOfBirth;
    private String motherEarTag;
    private Integer breedId;
    private Integer categoryId;
    private Integer farmId;
    private Integer sourceMarketId;
    private String cattleGroupId;
    private String conformationId;
    private String fatCoverId;
    private String conditionScore;
    private String healthScore;
    private String datePurchased;
    private String weightAtPurchase;
    private BigDecimal purchasePrice;
    private String numPrevMovements;
    private Integer saleId;
    private String weightAtSale;
    private String bodyWeight;
    private String salePrice;
    private BigDecimal expenses;
    private String comments;
    private String sireEarTag;
    private String previousHolding;
    private Integer fatteningFor;
    private Integer agentId;
    private String sireName;
    private String poundPerKgGain;
    private String hdDayFeeders;
    private String tagOrdered;
    private String tagHere;
    private String coopOpening;
    private String coopClosing;
    private String residencies;
    private Boolean newtagreq;
}
