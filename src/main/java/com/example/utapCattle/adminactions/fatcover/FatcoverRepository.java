package com.example.utapCattle.adminactions.fatcover;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FatcoverRepository extends JpaRepository<Fatcover, Long> {

}
