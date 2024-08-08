package com.example.utapCattle.model;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "cattle", schema = "public")
public class Cattle {
    @Id
    @Column(name = "eartag", length = 14)
    private String eartag;

    @Column(name = "tagid")
    private Integer tagid;

    @Column(name = "newtagreq")
    private Boolean newtagreq;

    @Column(name = "dateofbirth")
    private LocalDate dateofbirth;

    @Column(name = "mothereartag")
    private Integer mothereartag;

    @Column(name = "breedid")
    private Integer breedid;

    @Column(name = "categoryid")
    private Integer categoryid;

    @Column(name = "farmid")
    private Integer farmid;

    @Column(name = "sourcemarketid")
    private Integer sourcemarketid;

    @Column(name = "conformationid")
    private Integer conformationid;

    @Column(name = "fatcoverid")
    private Integer fatcoverid;

    @Column(name = "datepurchased")
    private LocalDate datepurchased;

    @Column(name = "weightatpurchase", precision = 4, scale = 2)
    private BigDecimal weightatpurchase;

    @Column(name = "purchaseprice", precision = 4, scale = 2)
    private BigDecimal purchaseprice;

    @Column(name = "saleid")
    private Integer saleid;

    @Column(name = "weightatsale", precision = 4, scale = 2)
    private BigDecimal weightatsale;

    @Column(name = "bodyweight", precision = 4, scale = 2)
    private BigDecimal bodyweight;

    @Column(name = "saleprice", precision = 4, scale = 2)
    private BigDecimal saleprice;

    @Column(name = "expenses", precision = 4, scale = 2)
    private BigDecimal expenses;

    @Column(name = "sireeartag", length = 14)
    private String sireeartag;

    @Column(name = "previousholding")
    private String previousholding;

    @Column(name = "fatteningfor")
    private Integer fatteningfor;

    @Column(name = "agentid")
    private Integer agentid;

    @Column(name = "sirename")
    private String sirename;

    @Override
    public String toString() {
        return "Cattle{" +
                "eartag='" + eartag + '\'' +
                ", tagid=" + tagid +
                ", newtagreq=" + newtagreq +
                ", dateofbirth=" + dateofbirth +
                ", mothereartag=" + mothereartag +
                ", breedid=" + breedid +
                ", categoryid=" + categoryid +
                ", farmid=" + farmid +
                ", sourcemarketid=" + sourcemarketid +
                ", conformationid=" + conformationid +
                ", fatcoverid=" + fatcoverid +
                ", datepurchased=" + datepurchased +
                ", weightatpurchase=" + weightatpurchase +
                ", purchaseprice=" + purchaseprice +
                ", saleid=" + saleid +
                ", weightatsale=" + weightatsale +
                ", bodyweight=" + bodyweight +
                ", saleprice=" + saleprice +
                ", expenses=" + expenses +
                ", sireeartag='" + sireeartag + '\'' +
                ", previousholding='" + previousholding + '\'' +
                ", fatteningfor=" + fatteningfor +
                ", agentid=" + agentid +
                ", sirename='" + sirename + '\'' +
                '}';
    }

}
