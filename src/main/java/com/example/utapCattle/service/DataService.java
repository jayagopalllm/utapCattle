package com.example.utapCattle.service;

import com.example.utapCattle.model.dto.AllDataDto;

public interface DataService {

	public AllDataDto getAllData();

	public AllDataDto getInductionData();

	public AllDataDto getTreatmentData();

	public AllDataDto getWeightAndTBTestData();
	
	public AllDataDto getSalesData();

}
