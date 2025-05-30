package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private Long customerId;
    private Long userFarmId;
    private String password;
    private String role;
    private String userName;

}
