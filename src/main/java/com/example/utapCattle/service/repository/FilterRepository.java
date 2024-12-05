package com.example.utapCattle.service.repository;

import com.example.utapCattle.model.entity.FilterCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends JpaRepository<FilterCriteria, Long> {
}
