package com.example.utapCattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.AllDataDto;
import com.example.utapCattle.service.DataService;

@RestController
public class DataController {
    
    @Autowired
    private DataService dataService;

    @GetMapping("/data")
    public AllDataDto getAllData() {
        return dataService.getAllData();
    }
}
