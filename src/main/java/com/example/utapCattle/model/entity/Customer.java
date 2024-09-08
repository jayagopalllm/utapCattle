package com.example.utapCattle.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer", schema = "public")
public class Customer {

    @Id
    @Column(name = "customerid")
    private Long customerId;

    @Column(name = "customername")
    private String customerName; 

    // Add getters and setters for all the fields here
    // ...
}

