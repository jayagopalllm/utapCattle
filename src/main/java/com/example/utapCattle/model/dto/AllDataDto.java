package com.example.utapCattle.model.dto;

import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.model.entity.Category;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.model.entity.DefaultTreatment;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.model.entity.MedicalCondition;
import com.example.utapCattle.model.entity.Medication;
import com.example.utapCattle.model.entity.Pen;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
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
	private List<DefaultTreatment> defaultTreatments;
	private Map<Long, String> eIdEarTagMap;
}
