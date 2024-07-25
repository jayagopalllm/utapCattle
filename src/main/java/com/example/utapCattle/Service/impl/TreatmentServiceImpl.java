package com.example.utapCattle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.Treatment;
import com.example.utapCattle.service.TreatmentService;
import com.example.utapCattle.service.repository.TreatmentRepository;

@Service
public class TreatmentServiceImpl implements TreatmentService{

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Override
    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    @Override
    public Treatment getTreatmentById(Long id) {
        return treatmentRepository.findById(id).orElse(null);
    }
 
}
