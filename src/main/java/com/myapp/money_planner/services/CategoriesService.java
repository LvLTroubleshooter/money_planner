package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Categories;
import com.myapp.money_planner.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public Categories createCategory(Categories category) {
        if (category.getCreatedAt() == null) {
            category.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Explicitly set createdAt
        }
        return categoriesRepository.save(category);
    }

    public Optional<Categories> getCategoryById(Long categoryId) {
        return categoriesRepository.findById(categoryId);
    }

    public Optional<Categories> getCategoryByName(String categoryName) {
        return categoriesRepository.findByCategoryName(categoryName);
    }

    public Categories updateCategory(Long categoryId, Categories category) {
        return categoriesRepository.findById(categoryId).map(existingCategory -> {
            category.setCategoryId(categoryId); // Ensure we're updating the correct category
            category.setCreatedAt(existingCategory.getCreatedAt()); // Preserve the existing createdAt value
            return categoriesRepository.save(category);
        }).orElse(null); // Return null if the category doesn't exist
    }

    public boolean deleteCategory(Long categoryId) {
        if (categoriesRepository.existsById(categoryId)) {
            categoriesRepository.deleteById(categoryId);
            return true;
        }
        return false;
    }
}
