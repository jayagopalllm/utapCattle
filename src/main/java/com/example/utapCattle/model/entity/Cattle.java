package com.example.utapCattle.model.entity;

import java.math.BigDecimal;

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
@Table(name = "cattle", schema = "public")
public class Cattle {
    
    @Id
    @Column(name = "cattleid")
    private Long cattleId;

    @Column(name = "eartag")
    private String earTag;

    @Column(name = "tagid")
    private Integer tagId; // Consider making this a foreign key if applicable

    @Column(name = "newtagreqd")
    private String newTagReqd; // Consider using a boolean or enum

    @Column(name = "dateofbirth")
    private String dateOfBirth; // Consider using a proper Date type if needed

    @Column(name = "mothereartag")
    private String motherEarTag;

    @Column(name = "breedid")
    private Integer breedId; // Consider making this a foreign key

    @Column(name = "categoryid")
    private Integer categoryId; // Consider making this a foreign key

    @Column(name = "farmid")
    private Integer farmId; // Consider making this a foreign key

    @Column(name = "sourcemarketid")
    private Integer sourceMarketId; // Consider making this a foreign key

    @Column(name = "cattlegroupid")
    private String cattleGroupId;

    @Column(name = "conformationid")
    private String conformationId;

    @Column(name = "fatcoverid")
    private String fatCoverId;

    @Column(name = "conditionscore")
    private String conditionScore;

    @Column(name = "healthscore")
    private String healthScore;

    @Column(name = "datepurchased")
    private String datePurchased; // Consider using a proper Date type

    @Column(name = "weightatpurchase")
    private String weightAtPurchase;

    @Column(name = "purchaseprice")
    private BigDecimal purchasePrice;

    @Column(name = "numprevmovements")
    private String numPrevMovements;

    @Column(name = "saleid")
    private Integer saleId; // Consider making this a foreign key

    @Column(name = "weightatsale")
    private String weightAtSale;

    @Column(name = "bodyweight")
    private String bodyWeight;

    @Column(name = "saleprice")
    private String salePrice; // Consider using BigDecimal if it's a price

    @Column(name = "expenses")
    private BigDecimal expenses;

    @Column(name = "comments")
    private String comments;

    @Column(name = "sireeartag")
    private String sireEarTag;

    @Column(name = "previousholding")
    private String previousHolding;

    @Column(name = "fatteningfor")
    private Integer fatteningFor; // Consider making this a foreign key

    @Column(name = "agentid")
    private Integer agentId; // Consider making this a foreign key

    @Column(name = "sirename")
    private String sireName;

    @Column(name = "poundperkggain")
    private String poundPerKgGain;

    @Column(name = "hddayfeeders")
    private String hdDayFeeders;

    @Column(name = "tagordered")
    private String tagOrdered; // Consider using a boolean or enum

    @Column(name = "taghere")
    private String tagHere; // Consider using a boolean or enum

    @Column(name = "coopopening")
    private String coopOpening;

    @Column(name = "coopclosing")
    private String coopClosing;

    @Column(name = "residencies")
    private String residencies;

    @Column(name = "newtagreq") 
    private Boolean newtagreq;

@Override
    public String toString() {
        return "Cattle{" +
                "cattleId=" + cattleId +
                ", earTag='" + earTag + '\'' +
                ", tagId=" + tagId +
                ", newTagReqd='" + newTagReqd + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", motherEarTag='" + motherEarTag + '\'' +
                ", breedId=" + breedId +
                ", categoryId=" + categoryId +
                ", farmId=" + farmId +
                ", sourceMarketId=" + sourceMarketId +
                ", cattleGroupId='" + cattleGroupId + '\'' +
                ", conformationId='" + conformationId + '\'' +
                ", fatCoverId='" + fatCoverId + '\'' +
                ", conditionScore='" + conditionScore + '\'' +
                ", healthScore='" + healthScore + '\'' +
                ", datePurchased='" + datePurchased + '\'' +
                ", weightAtPurchase='" + weightAtPurchase + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", numPrevMovements='" + numPrevMovements + '\'' +
                ", saleId=" + saleId +
                ", weightAtSale='" + weightAtSale + '\'' +
                ", bodyWeight='" + bodyWeight + '\'' +
                ", salePrice='" + salePrice + '\'' +
                ", expenses=" + expenses +
                ", comments='" + comments + '\'' +
                ", sireEarTag='" + sireEarTag + '\'' +
                ", previousHolding='" + previousHolding + '\'' +
                ", fatteningFor=" + fatteningFor +
                ", agentId=" + agentId +
                ", sireName='" + sireName + '\'' +
                ", poundPerKgGain='" + poundPerKgGain + '\'' +
                ", hdDayFeeders='" + hdDayFeeders + '\'' +
                ", tagOrdered='" + tagOrdered + '\'' +
                ", tagHere='" + tagHere + '\'' +
                ", coopOpening='" + coopOpening + '\'' +
                ", coopClosing='" + coopClosing + '\'' +
                ", residencies='" + residencies + '\'' +
                ", newtagreq=" + newtagreq +
                '}';
    }

}
