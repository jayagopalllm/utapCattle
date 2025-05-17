package com.example.utapCattle.adminactions.medication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.utapCattle.model.entity.Medication;

import java.util.List;

@RestController
@RequestMapping("/medications")
public class MedicationController {

    @Autowired
    private MedicationService service;

    @GetMapping
    public List<Medication> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medication> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Medication create(@RequestBody Medication condition) {
        return service.create(condition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medication> update(@PathVariable Long id, @RequestBody Medication condition) {
        try {
            return ResponseEntity.ok(service.update(id, condition));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
