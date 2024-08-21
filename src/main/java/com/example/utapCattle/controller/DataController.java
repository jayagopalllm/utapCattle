package com.example.utapCattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.AllDataDto;
import com.example.utapCattle.service.DataService;

@RestController
@RequestMapping("/data")
@CrossOrigin (origins = {"http://localhost:4200", "http://35.178.210.158"})
public class DataController extends BaseController{
    
    @Autowired
    private DataService dataService;

    @GetMapping()
    public AllDataDto getAllData() {
        logger.info("Incoming request: Retrieving all data");
        AllDataDto allData = dataService.getAllData();
        logger.info("Request successful: Retrieved all data"); 
        return allData;
    }
}
