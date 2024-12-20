package com.example.utapCattle.service;

import com.example.utapCattle.model.dto.AllDataDto;

public interface DataService {

	public AllDataDto getAllData();

	public AllDataDto getMedicalConditionData();

	public AllDataDto getTreatmentData();

	public AllDataDto getWeightAndTBTestData();
	
	public AllDataDto getSalesData();

	public AllDataDto getRegisterUserData();

	public AllDataDto getFilterData();

	public AllDataDto getSlaughtersHouse();

}
