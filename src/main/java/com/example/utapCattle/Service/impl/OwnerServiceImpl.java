package com.example.utapCattle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.Owner;
import com.example.utapCattle.service.OwnerService;
import com.example.utapCattle.service.repository.OwnerRepository;

@Service
public class OwnerServiceImpl implements OwnerService{

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }

    // Add more methods as needed (e.g., save, update, delete)
}
