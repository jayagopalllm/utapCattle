package com.example.utapCattle.model.entity;

public class AuthResponse {
    private Long userId;
    private String message;
    private String sessionId;
    private String username;
    private Long farmId;

    public AuthResponse(String message, String sessionId, Long userId, String username, Long userFarmId) {
        this.message = message;
        this.sessionId = sessionId;
        this.userId = userId;
        this.username = username;
        this.farmId = userFarmId;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long userFarmId) {
        this.farmId = userFarmId;
    }
}
