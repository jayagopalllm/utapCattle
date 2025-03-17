package com.example.utapCattle.security;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findBySessionId(String sessionId);
    Optional<UserSession> findByUserId(Long userId);
    void deleteBySessionId(String sessionId);
}
