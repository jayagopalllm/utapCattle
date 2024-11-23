package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmDto {
    
    private Long farmId;
    private String farmName;
    private String farmContact;
    private String address;
    private String holdingNumber;
    private String assuranceNumber;
    private String assuranceExpiryDate; // Consider using a proper Date type if applicable
    private String county;
    private String postcode;
    private String farmRef;
    private String current; 
}
