package com.example.utapCattle.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
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
	@Column(name = "id")
	private Long id;

	@Column(name = "cattleid")
	private Long cattleId;

	@Column(name = "prefix")
	private String prefix;

	@Column(name = "eartag")
	private String earTag;

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

	@Column(name = "datepurchased")
	private String datePurchased; // Consider using a proper Date type

	@Column(name = "purchaseprice")
	private BigDecimal purchasePrice;

	@Column(name = "saleid")
	private Long saleId; // Consider making this a foreign key

	@Column(name = "saleprice")
	private String salePrice; // Consider using BigDecimal if it's a price

	@Column(name = "comments")
	private String comments;

	@Column(name = "version")
	private String version;

	@Column(name = "previousholding")
	private String previousHolding;

	@Column(name = "fatteningfor")
	private Integer fatteningFor; // Consider making this a foreign key

	@Column(name = "agentid")
	private Integer agentId; // Consider making this a foreign key

	@Column(name = "conditionscore")
	private String conditionScore;

	@Column(name = "healthscore")
	private String healthScore;

	@Column(name = "weightatsale")
	private Double weightAtSale;

	@Column(name = "bodyweight")
	private String bodyWeight;

	@Column(name = "expenses")
	private BigDecimal expenses;

	@Column(name = "sireeartag")
	private String sireEarTag;

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

	@Column(name = "tagid")
	private Integer tagId; // Consider making this a foreign key if applicable

	@Column(name = "newtagreqd")
	private String newTagReqd; // Consider using a boolean or enum

	@Column(name = "cattlegroupid")
	private String cattleGroupId;

	@Column(name = "conformationid")
	private String conformationId;

	@Column(name = "fatcoverid")
	private String fatCoverId;

	@Column(name = "weightatpurchase")
	private String weightAtPurchase;

	@Column(name = "numprevmovements")
	private String numPrevMovements;

	@Column(name = "createdon")
	private LocalDate createdOn;

	@Column(name = "updatedon")
	private LocalDate updatedOn;

	@Column(name = "isinductioncompleted")
	private Boolean isInductionCompleted;

	@Column(name = "inductiondate")
	private LocalDate inductionDate;

	@Column(name = "ownerfarmid")
	private Long ownerFarmId;

	@Column(name = "userid")
	private Long userId;

	@PrePersist
	protected void onCreate() {
		createdOn = LocalDate.now(); // Store only the current date
	}

	@PreUpdate
	protected void onUpdate() {
		updatedOn = LocalDate.now(); // Update only the date
	}

	@Override
	public String toString() {
		return "Cattle{" + "cattleId=" + cattleId + ", prefix=" + prefix + ", earTag='" + earTag + '\''
				+ ", dateOfBirth='" + dateOfBirth + '\'' + ", motherEarTag='" + motherEarTag + '\'' + ", breedId="
				+ breedId + ", categoryId=" + categoryId + ", farmId=" + farmId + ", sourceMarketId=" + sourceMarketId
				+ ", datePurchased='" + datePurchased + '\'' + ", purchasePrice=" + purchasePrice + ", saleId=" + saleId
				+ ", salePrice='" + salePrice + '\'' + ", comments='" + comments + '\'' + ", version='" + version + '\''
				+ ", previousHolding='" + previousHolding + '\'' + ", flatteningFor=" + fatteningFor + ", agentId="
				+ agentId + ", conditionScore='" + conditionScore + '\'' + ", healthScore='" + healthScore + '\''
				+ ", weightAtSale='" + weightAtSale + '\'' + ", bodyWeight='" + bodyWeight + '\'' + ", expenses="
				+ expenses + ", sireEarTag='" + sireEarTag + '\'' + ", sireName='" + sireName + '\''
				+ ", poundPerKgGain='" + poundPerKgGain + '\'' + ", hdDayFeeders='" + hdDayFeeders + '\''
				+ ", tagOrdered='" + tagOrdered + '\'' + ", tagHere='" + tagHere + '\'' + ", coopOpening='"
				+ coopOpening + '\'' + ", coopClosing='" + coopClosing + '\'' + ", residencies='" + residencies + '\''
				+ ", newtagreq=" + newtagreq + ", tagId=" + tagId + ", newTagReqd='" + newTagReqd + '\''
				+ ", cattleGroupId='" + cattleGroupId + '\'' + ", conformationId='" + conformationId + '\''
				+ ", fatCoverId='" + fatCoverId + '\'' + ", weightAtPurchase='" + weightAtPurchase + '\''
				+ ", isInductionCompleted='" + isInductionCompleted + ", numPrevMovements='" + numPrevMovements + '\''
				+ '}';
	}

}
