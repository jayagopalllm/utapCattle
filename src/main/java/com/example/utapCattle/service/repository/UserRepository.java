package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserName(String userName);

    Optional<User> findByUserName(String username);

}
