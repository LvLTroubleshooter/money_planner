package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Incomesources;
import com.myapp.money_planner.services.IncomesourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomesources")
public class IncomesourcesController {

    private final IncomesourcesService incomesourcesService;

    @Autowired
    public IncomesourcesController(IncomesourcesService incomesourcesService) {
        this.incomesourcesService = incomesourcesService;
    }

    @PostMapping("/create")
    public ResponseEntity<Incomesources> createIncomeSource(@RequestBody Incomesources incomeSource) {
        Incomesources createdSource = incomesourcesService.createIncomeSource(incomeSource);
        return ResponseEntity.ok(createdSource);
    }

    @GetMapping("/{sourceId}")
    public ResponseEntity<Incomesources> getIncomeSourceById(@PathVariable Long sourceId) {
        Optional<Incomesources> incomeSource = incomesourcesService.getIncomeSourceById(sourceId);
        return incomeSource.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Incomesources>> getIncomeSourcesByUserId(@PathVariable Long userId) {
        List<Incomesources> incomeSources = incomesourcesService.getIncomeSourcesByUserId(userId);
        if (!incomeSources.isEmpty()) {
            return ResponseEntity.ok(incomeSources);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}/name/{sourceName}")
    public ResponseEntity<List<Incomesources>> getIncomeSourcesByUserIdAndSourceName(
            @PathVariable Long userId, @PathVariable String sourceName) {
        List<Incomesources> incomeSources = incomesourcesService.getIncomeSourcesByUserIdAndSourceName(userId, sourceName);
        if (!incomeSources.isEmpty()) {
            return ResponseEntity.ok(incomeSources);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/user/{userId}/{sourceId}")
    public ResponseEntity<Incomesources> updateIncomeSource(
            @PathVariable Long userId, @PathVariable Long sourceId, @RequestBody Incomesources incomeSource) {
        Incomesources updatedSource = incomesourcesService.updateIncomeSource(userId, sourceId, incomeSource);
        return updatedSource != null ? ResponseEntity.ok(updatedSource) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{sourceId}")
    public ResponseEntity<Void> deleteIncomeSource(@PathVariable Long sourceId) {
        boolean deleted = incomesourcesService.deleteIncomeSource(sourceId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
