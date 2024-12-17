package com.myapp.money_planner.services;

import com.myapp.money_planner.models.Categories;
import com.myapp.money_planner.models.Users;
import com.myapp.money_planner.repositories.CategoriesRepository;
import com.myapp.money_planner.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriesService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriesService.class);

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private UsersRepository usersRepository;

    public List<Categories> getCategoriesByUser(Long userId) {
        logger.info("Getting categories for user: {}", userId);
        try {
            if (!usersRepository.existsById(userId)) {
                logger.error("User not found with ID: {}", userId);
                throw new RuntimeException("User not found");
            }

            List<Categories> categories = categoriesRepository.findByUser_UserId(userId);
            logger.info("Found {} categories for user {}", categories.size(), userId);
            categories.forEach(category -> logger.debug("Category: id={}, name={}, createdAt={}",
                    category.getCategoryId(),
                    category.getCategoryName(),
                    category.getCreatedAt()));
            return categories;
        } catch (Exception e) {
            logger.error("Error retrieving categories for user {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Error retrieving categories: " + e.getMessage(), e);
        }
    }

    public Categories createCategory(Categories category) {
        logger.info("Creating category: {}", category);
        try {
            // Ensure user exists
            Optional<Users> userOptional = usersRepository.findById(category.getUser().getUserId());
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            category.setUser(userOptional.get());
            category.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            Categories savedCategory = categoriesRepository.save(category);
            logger.info("Created category with ID: {}", savedCategory.getCategoryId());
            return savedCategory;
        } catch (Exception e) {
            logger.error("Error creating category: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating category: " + e.getMessage(), e);
        }
    }

    public Categories updateCategory(Long userId, Categories category) {
        Optional<Categories> existingCategory = categoriesRepository.findByCategoryIdAndUser_UserId(
                category.getCategoryId(), userId);

        if (existingCategory.isPresent()) {
            Categories categoryToUpdate = existingCategory.get();
            categoryToUpdate.setCategoryName(category.getCategoryName());
            // Preserve the original createdAt timestamp
            return categoriesRepository.save(categoryToUpdate);
        }
        return null;
    }

    public boolean deleteCategory(Long userId, Long categoryId) {
        Optional<Categories> category = categoriesRepository.findByCategoryIdAndUser_UserId(categoryId, userId);
        if (category.isPresent()) {
            categoriesRepository.delete(category.get());
            return true;
        }
        return false;
    }
}
