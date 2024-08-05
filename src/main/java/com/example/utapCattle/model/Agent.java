package com.example.utapCattle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agent", schema = "public")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Or GenerationType.IDENTITY if desired
    @Column(name = "agentid")
    private long agentId;

    @Column(name = "agentname", nullable = false)
    private String agentName;

    @Override
    public String toString() {
        return "Agent{" +
                "agentId=" + agentId +
                ", agentName='" + agentName + '\'' +
                '}';
    }
}