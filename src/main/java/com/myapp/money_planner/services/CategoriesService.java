package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Categories;
import com.myapp.money_planner.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Categories> getCategoriesByUser(Long userId) {
        return categoriesRepository.findByUser_UserId(userId);
    }

    public Categories createCategory(Categories category) {
        return categoriesRepository.save(category);
    }
}
