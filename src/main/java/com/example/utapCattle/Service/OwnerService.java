package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.Owner;

public interface OwnerService {

    public List<Owner> getAllOwners() ;

    public Owner getOwnerById(Long id) ;
    
}
