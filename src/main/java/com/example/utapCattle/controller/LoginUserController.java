package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.LoginDto;
import com.example.utapCattle.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loginuser")
public class LoginUserController {

    private final LoginService loginService;

    public LoginUserController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateUserCredentials() {

            return ResponseEntity.ok("{ \"email\": \"jay@gmail.com\", \"password\": \"mypassword\" }");

    }

}
