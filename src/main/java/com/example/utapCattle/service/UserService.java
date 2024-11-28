package com.example.utapCattle.service;

import com.example.utapCattle.model.dto.UserDto;
import com.example.utapCattle.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto getByUserId(Long id);

    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto user);

}
