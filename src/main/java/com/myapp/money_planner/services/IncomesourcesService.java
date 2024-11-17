package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Incomesources;
import com.myapp.money_planner.repositories.IncomesourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncomesourcesService {

    @Autowired
    private IncomesourcesRepository incomesourcesRepository;

    // Create
    public Incomesources createIncomeSource(Incomesources incomeSource) {
        return incomesourcesRepository.save(incomeSource);
    }

    // Read
    public Optional<Incomesources> getIncomeSourceById(Long sourceId) {
        return incomesourcesRepository.findById(sourceId);
    }

    public Optional<Incomesources> getIncomeSourceByUserId(Long userId) {
        return incomesourcesRepository.findByUser_UserId(userId);
    }

    public Optional<Incomesources> getIncomeSourceByName(String sourceName) {
        return incomesourcesRepository.findBySourceName(sourceName);
    }

    // Update
    public Incomesources updateIncomeSource(Long sourceId, Incomesources incomeSource) {
        if (incomesourcesRepository.existsById(sourceId)) {
            incomeSource.setSourceId(sourceId); // Ensure we're updating the correct source
            return incomesourcesRepository.save(incomeSource);
        }
        return null; // Return null if the source doesn't exist
    }

    // Delete
    public boolean deleteIncomeSource(Long sourceId) {
        if (incomesourcesRepository.existsById(sourceId)) {
            incomesourcesRepository.deleteById(sourceId);
            return true;
        }
        return false; // Return false if the source doesn't exist
    }
}
