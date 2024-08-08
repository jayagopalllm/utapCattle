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
@Table(name = "category", schema = "public")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "categoryid")
    private Integer categoryId;

    @Column(name = "categorydesc")
    private String categoryDesc;

    @Column(name = "sex")
    private String sex;

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryDesc='" + categoryDesc + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

}