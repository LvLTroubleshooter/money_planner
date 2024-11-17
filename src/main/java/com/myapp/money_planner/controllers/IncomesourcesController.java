package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Incomesources;
import com.myapp.money_planner.services.IncomesourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/incomesources")
public class IncomesourcesController {

    private final IncomesourcesService incomesourcesService;

    @Autowired
    public IncomesourcesController(IncomesourcesService incomesourcesService) {
        this.incomesourcesService = incomesourcesService;
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<Incomesources> createIncomeSource(@RequestBody Incomesources incomeSource) {
        Incomesources createdSource = incomesourcesService.createIncomeSource(incomeSource);
        return ResponseEntity.ok(createdSource);
    }

    // Read by ID
    @GetMapping("/{sourceId}")
    public ResponseEntity<Incomesources> getIncomeSourceById(@PathVariable Long sourceId) {
        Optional<Incomesources> incomeSource = incomesourcesService.getIncomeSourceById(sourceId);
        return incomeSource.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Incomesources> getIncomeSourceByUserId(@PathVariable Long userId) {
        Optional<Incomesources> incomeSource = incomesourcesService.getIncomeSourceByUserId(userId);
        return incomeSource.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by Source Name
    @GetMapping("/name/{sourceName}")
    public ResponseEntity<Incomesources> getIncomeSourceByName(@PathVariable String sourceName) {
        Optional<Incomesources> incomeSource = incomesourcesService.getIncomeSourceByName(sourceName);
        return incomeSource.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{sourceId}")
    public ResponseEntity<Incomesources> updateIncomeSource(@PathVariable Long sourceId, @RequestBody Incomesources incomeSource) {
        Incomesources updatedSource = incomesourcesService.updateIncomeSource(sourceId, incomeSource);
        return updatedSource != null ? ResponseEntity.ok(updatedSource) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{sourceId}")
    public ResponseEntity<Void> deleteIncomeSource(@PathVariable Long sourceId) {
        boolean deleted = incomesourcesService.deleteIncomeSource(sourceId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
