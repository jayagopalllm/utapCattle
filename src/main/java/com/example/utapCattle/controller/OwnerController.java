package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.service.impl.OwnerService;
import com.example.utapCattle.model.Owner;

@RestController
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/owners/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        Owner owner = ownerService.getOwnerById(id);
        if (owner != null) {
            return ResponseEntity.ok(owner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/owners") // Endpoint for fetching all owners
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }
    
}
