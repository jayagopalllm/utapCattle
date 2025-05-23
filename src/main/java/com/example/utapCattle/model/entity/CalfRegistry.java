package com.example.utapCattle.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "calf_registry")
public class CalfRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="eartag",nullable = false)
    private String eartag;

    @Column(name="breed",nullable = false)
    private String breed;

    @Column(name="category",nullable = false)
    private String category;

    @Column(name="date_of_birth",nullable = false)
    private LocalDate dateOfBirth;

    @Column(name="sex",nullable = false, length = 8)
    private String sex;

    @Column(name="birth_weight")
    private Double birthWeight;

    @Column(name="dam_eartag")
    private String damEartag;

    @Column(name="sire_eartag")
    private String sireEartag;

    @Column(name="sire_name")
    private String sireName;

    @Column(name="ease_of_calving",nullable = false, length = 32)
    private String easeOfCalving;

    @Column(name="holding_no",nullable = false, length = 16)
    private String holdingNo;

    @Column(name="notes",columnDefinition = "TEXT")
    private String notes;

    @Column(name="user_farm_id",nullable = false)
    private Long userFarmId;

    @Column(name="created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
