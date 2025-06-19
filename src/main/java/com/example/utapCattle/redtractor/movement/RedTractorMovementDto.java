package com.example.utapCattle.redtractor.movement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedTractorMovementDto {
    private String dateIn;
    private String earNumber;
    private String dateOfBirth;
    private String sex;
    private String breed;
    private String bought;
    private String diedOn;
    private String soldOn;
    private String deathReason;
}
