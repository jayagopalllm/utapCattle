package com.example.utapCattle.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.utapCattle.model.entity.CalfRegistry;
import com.example.utapCattle.service.CalfRegistryService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/calf-registry")
public class CalfRegistryController {

    @Autowired
    private CalfRegistryService service;


    @PostMapping
    public CalfRegistry create(@RequestBody CalfRegistry calf,HttpServletRequest request) {
        Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
        calf.setUserFarmId(userFarmId);
        return service.save(calf);
    }

}
