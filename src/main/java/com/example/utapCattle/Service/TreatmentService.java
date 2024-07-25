package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.Treatment;

public interface TreatmentService {

    public List<Treatment> getAllTreatments() ;

    public Treatment getTreatmentById(Long id) ;
    
}
