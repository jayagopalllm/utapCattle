package com.example.utapCattle.service;

import com.example.utapCattle.model.dto.AllDataDto;

public interface DataService {

	public AllDataDto getAllData(Long userFarmId);

	public AllDataDto getMedicalConditionData(Long userId, Long userFarmId);

	public AllDataDto getTreatmentData(Long userId,Long userFarmId);

	public AllDataDto getWeightAndTBTestData(Long userId, Long userFarmId);

	public AllDataDto getSalesData(Long userId, Long userFarmId);

	public AllDataDto getRegisterUserData();

	public AllDataDto getFilterData();

	public AllDataDto getSlaughtersHouse(Long userFarmId);

}
