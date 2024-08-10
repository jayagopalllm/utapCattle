package com.example.utapCattle.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "customerid")
    private Integer customerId;

    @Column(name = "customername")
    private String customerName; 

    // Add getters and setters for all the fields here
    // ...
}

