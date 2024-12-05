package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.UserDto;
import com.example.utapCattle.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
