package com.example.utapCattle.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cts_movement", schema = "public")
public class CtsMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "sl_number")
    private String slNumber;

    @Column(name = "identity_type")
    private String identityType;

    @Column(name = "eartag_id")
    private String eartagId;

    @Column(name = "cts_identity")
    private String ctsIdentity;

    @Column(name = "cts_eartag")
    private String ctsEartag;

    @Column(name = "date_of_birth")
    private String dateOfBirth; // Use String if the date format is not standard or needs parsing

    @Column(name = "sex")
    private String sex;

    @Column(name = "date_of_movement")
    private String dateOfMovement;

    @Column(name = "movement_type")
    private String movementType;

    @Column(name = "movement_direction")
    private String movementDirection;

    @Column(name = "type_of_holding")
    private String typeOfHolding;

    @Column(name = "location_or_holding_cph")
    private String locationOrHoldingCph;

    @Column(name = "external_sub_location")
    private String externalSubLocation;

    @Column(name = "movement_receipt_date")
    private String movementReceiptDate;

    @Column(name = "col16")
    private String col16;

    @Column(name = "breed")
    private String breed;

    @Column(name = "recorded_identity_of_genetic_dam")
    private String recordedIdentityOfGeneticDam;

    @Column(name = "col19")
    private String col19;

    @Column(name = "identity_of_sire")
    private String identityOfSire;

    @Column(name = "version_of_passport")
    private String versionOfPassport;

    @Column(name = "col22")
    private String col22;

    @Column(name = "hashkey", unique = true)
    private String hashkey;
}
