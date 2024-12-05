package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, String> {

    Optional<Login> findByUsername(String username);
}
