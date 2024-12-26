package com.example.utapCattle.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.utapCattle.model.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customer WHERE id = :id", nativeQuery = true)
    List<Object[]> findCustomerDataById(@Param("id") String id);

}
