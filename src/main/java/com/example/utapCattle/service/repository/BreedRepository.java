package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    Optional<Breed> findByBreedid(Long breedid);
}
