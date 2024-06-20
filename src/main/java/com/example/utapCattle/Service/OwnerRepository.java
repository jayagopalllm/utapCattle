package com.example.utapCattle.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {}

