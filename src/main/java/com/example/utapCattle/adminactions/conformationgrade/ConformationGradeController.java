package com.example.utapCattle.adminactions.conformationgrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conformationgrade")
public class ConformationGradeController {

    @Autowired
    private ConformationGradeService service;

    @GetMapping
    public List<ConformationGrade> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConformationGrade> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ConformationGrade create(@RequestBody ConformationGrade condition) {
        return service.create(condition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConformationGrade> update(@PathVariable Long id, @RequestBody ConformationGrade condition) {
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
