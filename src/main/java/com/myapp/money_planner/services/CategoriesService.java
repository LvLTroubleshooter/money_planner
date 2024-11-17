package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Categories;
import com.myapp.money_planner.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    // Create
    public Categories createCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    // Read
    public Optional<Categories> getCategoryById(Long categoryId) {
        return categoriesRepository.findById(categoryId);
    }

    public Optional<Categories> getCategoryByUserId(Long userId) {
        return categoriesRepository.findByUser_UserId(userId);
    }

    public Optional<Categories> getCategoryByName(String categoryName) {
        return categoriesRepository.findByCategoryName(categoryName);
    }

    // Update
    public Categories updateCategory(Long categoryId, Categories category) {
        if (categoriesRepository.existsById(categoryId)) {
            category.setCategoryId(categoryId); // Ensure we're updating the correct category
            return categoriesRepository.save(category);
        }
        return null; // Return null if the category doesn't exist
    }

    // Delete
    public boolean deleteCategory(Long categoryId) {
        if (categoriesRepository.existsById(categoryId)) {
            categoriesRepository.deleteById(categoryId);
            return true;
        }
        return false; // Return false if the category doesn't exist
    }
}
