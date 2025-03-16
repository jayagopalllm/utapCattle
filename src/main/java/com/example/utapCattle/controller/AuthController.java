package com.example.utapCattle.controller;


import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.utapCattle.model.entity.AuthResponse;
import com.example.utapCattle.model.entity.LoginRequest;
import com.example.utapCattle.model.entity.User;
import com.example.utapCattle.security.UserSession;
import com.example.utapCattle.security.UserSessionRepository;
import com.example.utapCattle.service.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserSessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        
        Optional<User> optionalUser = userRepository.findByUserName(request.getUsername());

        if (optionalUser.isEmpty() || !optionalUser.get().getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Invalid credentials", null, null));
        }

        User user = optionalUser.get();

       
        Optional<UserSession> existingSession = sessionRepository.findByUserId(user.getId());

        existingSession.ifPresent(sessionRepository::delete);

       
        UserSession userSession = new UserSession(user.getId(), session.getId(), true);
        sessionRepository.save(userSession);

        System.out.println("userSession: " + userSession);

        return ResponseEntity.ok(new AuthResponse("Login successful", session.getId(), user.getId()));
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        sessionRepository.deleteBySessionId(session.getId());
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}
