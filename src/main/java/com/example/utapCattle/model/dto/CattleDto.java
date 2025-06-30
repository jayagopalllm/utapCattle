package com.example.utapCattle.model.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CattleDto {
	private Long id;
	private Long cattleId;
	private String prefix;
	private String earTag;
	private String dateOfBirth;
	private String motherEarTag;
	private Integer breedId;
	private Integer categoryId;
	private String breedName;
	private String categoryName;
	private String sex;
	private Integer farmId;
	private String farmName;
	private Integer sourceMarketId;
	private String sourceMarketName;
	private String datePurchased;
	private BigDecimal purchasePrice;
	private Long saleId;
	private String salePrice;
	private String comments;
	private String version;
	private String previousHolding;
	private Integer fatteningFor;
	private String fatteningForName;
	private Integer agentId;
	private String agentName;
	private String conditionScore;
	private String healthScore;
	private Double weightAtSale;
	private Double dlwgFarm;
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
	private Boolean newTagReq;
	private Integer tagId;
	private String cattleGroupId;
	private Long conformationId;
	private Long fatCoverId;
	private String weightAtPurchase;
	private String numPrevMovements;
	private Long totalTreatments;
	private String lastWithdraw;
	private Boolean isInductionCompleted;
	private String ageInMonths;
	private BigDecimal ratePerKg;
	private BigDecimal ratePerDay;
	private List<CommentDto> commentsList;
}
