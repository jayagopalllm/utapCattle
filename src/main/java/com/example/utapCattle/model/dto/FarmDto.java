package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmDto {
    
    private Long farmId;
    private String farmContact;
    private String farmName;
    private String address;
    private String holdingNumber;
    private String assuranceNumber;
    private String assuranceExpiryDate; // Consider using a proper Date type if applicable
    private String county;
    private String postcode;
    private String farmRef;
    private String current; 
}
