package com.example.utapCattle.adminactions.penGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pengroups")
public class PenGroupController {

    @Autowired
    private PenGroupService service;

    @GetMapping
    public List<PenGroup> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PenGroup> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PenGroup create(@RequestBody PenGroup condition) {
        return service.create(condition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PenGroup> update(@PathVariable Long id, @RequestBody PenGroup condition) {
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
