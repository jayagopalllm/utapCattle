package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.UserDto;
import com.example.utapCattle.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://35.178.210.158")
public class RegisterUserController extends BaseController{

    private final UserService userService;

    public RegisterUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getAgentById(@PathVariable Long id) {
        try {
            UserDto user = userService.getByUserId(id);
            if (user != null) {
                logger.info("Retrieved user with ID: {}", id);
                return ResponseEntity.ok(user);
            } else {
                logger.warn("User not found for ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve Agent with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllAgents() {
        try {
            List<UserDto> users = userService.getAllUsers();
            if (!users.isEmpty()) {
                logger.info("Retrieved {} Users", users.size());
            } else {
                logger.warn("No Users found");
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Exception occurred: Unable to retrieve Users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveUser(@RequestBody UserDto user, HttpServletRequest request) {
        logger.info("Saving new User: {}", user);
        try {
            Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
            user.setUserFarmId(userFarmId);
            UserDto savedUserDto = userService.saveUser(user);
            logger.info("Saved Agent with ID: {}", savedUserDto.getId());
            return new ResponseEntity<>(savedUserDto.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            //Log and rethrow exception , exception handled in GlobalExceptionHandler
            logger.error("Exception occurred: Unable to save Agent", e);
            throw e;
        }
    }

}
