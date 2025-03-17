package com.example.utapCattle.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = request.getHeader("Session-ID");
        String userId = request.getHeader("User-ID");

        if (sessionId == null || userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing session ID or user ID");
            return false;
        }

        // Validate session from DB
        Optional<UserSession> sessionOptional = userSessionRepository.findBySessionId(sessionId);

        if (sessionOptional.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired session");
            return false;
        }

        UserSession session = sessionOptional.get();

        if (!session.getUserId().toString().equals(userId) || !session.isActive()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired session");
            return false;
        }

        return true;
    }
}
