package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.UserDto;
import com.example.utapCattle.model.entity.User;
import com.example.utapCattle.service.UserService;
import com.example.utapCattle.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder encoder;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto getByUserId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToDto(user);
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }


    @Override
    public UserDto saveUser(UserDto userDto) {
        User user1 = new User();
        if (userRepository.existsByUserName(userDto.getUserName())) {
            throw new IllegalArgumentException("Username already exists: " + userDto.getUserName());
        }
        Long nextId = userRepository.getNextSequenceValue();
        user1.setUserName(userDto.getUserName());
        user1.setCustomerId(userDto.getCustomerId());
        user1.setRole(userDto.getRole());
        user1.setFarmId(userDto.getFarmId());
        user1.setPassword(encoder.encode(userDto.getPassword()));
        user1.setId(nextId);

        user1 = userRepository.save(user1);
        return mapToDto(user1);
    }


    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setCustomerId(user.getCustomerId());
        userDto.setFarmId(user.getFarmId()); // Avoid sending raw passwords; use hashed password or omit
        userDto.setRole(user.getRole());
        userDto.setUserName(user.getUserName());
        return userDto;
    }
}
