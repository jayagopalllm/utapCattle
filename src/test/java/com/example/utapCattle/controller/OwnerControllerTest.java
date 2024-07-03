package com.example.utapCattle.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.utapCattle.model.Owner;
import com.example.utapCattle.service.impl.OwnerServiceImpl;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerServiceImpl ownerService;

    @Test
    public void testGetOwnerById_ExistingOwner() throws Exception {
        // Mock data
        Long ownerId = 1L;
        Owner mockOwner = new Owner(ownerId, "John Doe", 30);

        // Mock service behavior
        when(ownerService.getOwnerById(ownerId)).thenReturn(mockOwner);

        // Perform the GET request
        mockMvc.perform(get("/owners/{id}", ownerId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ownerId))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    public void testGetOwnerById_NonExistingOwner() throws Exception {
        // Mock data
        Long ownerId = 999L;

        // Mock service behavior
        when(ownerService.getOwnerById(ownerId)).thenReturn(null);

        // Perform the GET request
        mockMvc.perform(get("/owners/{id}", ownerId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}