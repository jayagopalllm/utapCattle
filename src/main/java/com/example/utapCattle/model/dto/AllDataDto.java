package com.example.utapCattle.model.dto;

import java.util.List;

import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.model.entity.Category;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.model.entity.MedicalCondition;
import com.example.utapCattle.model.entity.Medication;
import com.example.utapCattle.model.entity.Pen;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllDataDto {

	private List<Farm> sourceFarm;
	private List<Breed> breed;
	private List<Market> market;
	private List<Category> category;
	private List<Agent> agent;
	private List<Customer> fatteningFor;
	private List<MedicalCondition> medicalCondition;
	private List<Medication> medication;
	private List<String> earTagList;
	private List<String> eIdList;
	private List<Pen> pens;

	public AllDataDto(final List<Farm> sourceFarm, final List<Breed> breed, final List<Market> market,
			final List<Category> category, final List<Agent> agent, final List<Customer> fatteningFor) {
		this.sourceFarm = sourceFarm;
		this.breed = breed;
		this.market = market;
		this.category = category;
		this.agent = agent;
		this.fatteningFor = fatteningFor;
	}

	public AllDataDto(final List<MedicalCondition> medicalCondition, final List<Medication> medication,
			final List<Pen> pens, List<String> eIdList, final List<String> earTagList) {
		this.medicalCondition = medicalCondition;
		this.medication = medication;
		this.pens = pens;
		this.eIdList = eIdList;
		this.earTagList = earTagList;
	}
}
