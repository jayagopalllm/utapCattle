package com.example.utapCattle.model.dto;

import java.util.List;

import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.model.entity.Category;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.model.entity.Market;

import lombok.Data;

@Data
public class AllDataDto {

    private List<Farm> source_farm;
    private List<Breed> breed;
    private List<Market> market;
    private List<Category> category;
    private List<Agent> agent;
    private List<Customer> flattening_for;

    public AllDataDto(List<Farm> source_farm, List<Breed> breed, List<Market> market, List<Category> category,
            List<Agent> agent, List<Customer> flattening_for) {
        this.source_farm = source_farm;
        this.breed = breed;
        this.market = market;
        this.category = category;
        this.agent = agent;
        this.flattening_for = flattening_for;
    }
}
