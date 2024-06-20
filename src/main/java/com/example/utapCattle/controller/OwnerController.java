package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.Service.Impl.OwnerService;
import com.example.utapCattle.model.Owner;

@RestController
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/owners/{id}")
    public Owner getOwnerById(@PathVariable Long id) {
        Owner owner = ownerService.getOwnerById(id);
        return owner;
    }


    @GetMapping("/owners") // Endpoint for fetching all owners
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/owners/string/{id}") // Endpoint for fetching owner as string
    public String getOwnerAsString(@PathVariable Long id) {
        Owner owner = ownerService.getOwnerById(id);
        if (owner != null) {
            return owner.toString(); // Assuming Owner has a sensible toString() method
        } else {
            return "Owner not found";
        }
    }
    
}
