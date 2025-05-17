package com.example.utapCattle.adminactions.medicationType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicationTypes")
public class MedicationTypeController {

    @Autowired
    private MedicationTypeService service;

    @GetMapping
    public List<MedicationType> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationType> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MedicationType create(@RequestBody MedicationType condition) {
        return service.create(condition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationType> update(@PathVariable Long id, @RequestBody MedicationType condition) {
        try {
            return ResponseEntity.ok(service.update(id, condition));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
