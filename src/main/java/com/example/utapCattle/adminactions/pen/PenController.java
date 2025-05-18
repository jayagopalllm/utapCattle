package com.example.utapCattle.adminactions.pen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.utapCattle.model.entity.Pen;

import java.util.List;

@RestController
@RequestMapping("/pens")
public class PenController {

    @Autowired
    private PenService service;

    @GetMapping
    public List<Pen> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pen> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pen create(@RequestBody Pen condition) {
        return service.create(condition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pen> update(@PathVariable Long id, @RequestBody Pen condition) {
        try {
            return ResponseEntity.ok(service.update(id, condition));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
