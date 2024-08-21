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
	private String prefix;
	private String earTag;
	private String dateOfBirth;
	private String motherEarTag;
	private Integer breedId;
	private Integer categoryId;
	private Integer farmId;
	private Integer sourceMarketId;
	private String datePurchased;
	private BigDecimal purchasePrice;
	private Integer saleId;
	private String salePrice;
	private String comments;
	private String version;
	private String previousHolding;
	private Integer flatteningFor;
	private Integer agentId;
	private String conditionScore;
	private String healthScore;
	private String weightAtSale;
	private String bodyWeight;
	private BigDecimal expenses;
	private String sireEarTag;
	private String sireName;
	private String poundPerKgGain;
	private String hdDayFeeders;
	private String tagOrdered;
	private String tagHere;
	private String coopOpening;
	private String coopClosing;
	private String residencies;
	private Boolean newtagreq;
	private Integer tagId;
	private String newTagReqd;
	private String cattleGroupId;
	private String conformationId;
	private String fatCoverId;
	private String weightAtPurchase;
	private String numPrevMovements;
}
