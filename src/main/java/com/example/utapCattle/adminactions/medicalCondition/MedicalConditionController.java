package com.example.utapCattle.adminactions.medicalCondition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.utapCattle.model.entity.MedicalCondition;

import java.util.List;

@RestController
@RequestMapping("/medicalConditions")
public class MedicalConditionController {

    @Autowired
    private MedicalConditionService service;

    @GetMapping
    public List<MedicalCondition> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalCondition> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MedicalCondition create(@RequestBody MedicalCondition condition) {
        return service.create(condition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalCondition> update(@PathVariable Long id, @RequestBody MedicalCondition condition) {
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
