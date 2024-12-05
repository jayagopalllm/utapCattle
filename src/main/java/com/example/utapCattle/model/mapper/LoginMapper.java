package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.LoginDto;
import com.example.utapCattle.model.entity.Login;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginDto toDto(Login login);

    Login toEntity(LoginDto loginDto);
}
