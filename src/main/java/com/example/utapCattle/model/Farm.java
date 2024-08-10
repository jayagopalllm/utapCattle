package com.example.utapCattle.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "farm", schema = "public") // Explicitly specify the table name
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming auto-increment for 'farmid'
    @Column(name = "farmid")
    private Integer farmId;

    @Column(name = "farmcontact")
    private String farmContact;

    @Column(name = "farmname")
    private String farmName;

    @Column(name = "address")
    private String address;

    @Column(name = "holdingnumber")
    private String holdingNumber;

    @Column(name = "assurancenumber")
    private String assuranceNumber;

    @Column(name = "assuranceexpirydate")
    private String assuranceExpiryDate; // Consider using a proper Date type if applicable

    @Column(name = "county")
    private String county;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "farmref")
    private String farmRef;

    @Column(name = "current")
    private String current; // Consider using a boolean or enum if appropriate

    // Add getters and setters for all the fields here
    // ...
}
