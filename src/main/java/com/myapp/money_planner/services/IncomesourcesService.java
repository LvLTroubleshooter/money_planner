package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Incomesources;
import com.myapp.money_planner.repositories.IncomesourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class IncomesourcesService {

    @Autowired
    private IncomesourcesRepository incomesourcesRepository;

    public Incomesources createIncomeSource(Incomesources incomeSource) {
        if (incomeSource.getCreatedAt() == null) {
            incomeSource.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }
        return incomesourcesRepository.save(incomeSource);
    }

    public Optional<Incomesources> getIncomeSourceById(Long sourceId) {
        return incomesourcesRepository.findById(sourceId);
    }

    public List<Incomesources> getIncomeSourcesByUserId(Long userId) {
        return incomesourcesRepository.findByUser_UserId(userId);
    }

    public List<Incomesources> getIncomeSourcesByUserIdAndSourceName(Long userId, String sourceName) {
        return incomesourcesRepository.findByUser_UserIdAndSourceName(userId, sourceName);
    }

    public Incomesources updateIncomeSource(Long userId, Long sourceId, Incomesources incomeSource) {
        Optional<Incomesources> existingIncomeSource = incomesourcesRepository.findByUser_UserIdAndSourceId(userId, sourceId);
        if (existingIncomeSource.isPresent()) {
            Incomesources sourceToUpdate = existingIncomeSource.get();
            sourceToUpdate.setSourceName(incomeSource.getSourceName());
            sourceToUpdate.setAmount(incomeSource.getAmount());
            return incomesourcesRepository.save(sourceToUpdate);
        }
        return null;
    }

    public boolean deleteIncomeSource(Long sourceId) {
        if (incomesourcesRepository.existsById(sourceId)) {
            incomesourcesRepository.deleteById(sourceId);
            return true;
        }
        return false;
    }
}
