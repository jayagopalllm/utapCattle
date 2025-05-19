package com.example.utapCattle.adminactions.fatcover;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fatcover")
public class FatcoverController {

    @Autowired
    private FatcoverService service;

    @GetMapping
    public List<Fatcover> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fatcover> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Fatcover create(@RequestBody Fatcover condition) {
        return service.create(condition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fatcover> update(@PathVariable Long id, @RequestBody Fatcover condition) {
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
