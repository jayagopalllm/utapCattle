package com.example.utapCattle.adminactions.medicationSupplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/medicationSuppliers")
public class MedicationSupplierController {

    @Autowired
    private MedicationSupplierService service;

    @GetMapping
    public List<MedicationSupplier> getAll(HttpServletRequest request) {
        Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
        return service.getAll(userFarmId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationSupplier> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MedicationSupplier create(@RequestBody MedicationSupplier condition, HttpServletRequest request) {
        Long userFarmId = Long.parseLong(request.getHeader("Farm-ID"));
        condition.setUserFarmId(userFarmId);
        return service.create(condition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationSupplier> update(@PathVariable Long id, @RequestBody MedicationSupplier condition) {
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
