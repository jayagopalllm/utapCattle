package com.example.utapCattle.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "death_record")
public class DeathRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eartag")
    private String earTag;

    @Column(name = "death_date")
    private LocalDate deathDate;

    @Column(name = "death_reason")
    private String deathReason;

    @Column(name = "abattoir")
    private String abattoir;

    @Column(name = "other_location")
    private String otherLocation;

    @Column(name = "comments")
    private String comments;

    @Column(name = "createdon")
	private LocalDateTime createdOn;

	@Column(name = "updatedon")
	private LocalDateTime updatedOn;

    @Transient
    private List<String> earTags;

    @PrePersist
	protected void onCreate() {
		createdOn = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedOn = LocalDateTime.now();
	}
}
