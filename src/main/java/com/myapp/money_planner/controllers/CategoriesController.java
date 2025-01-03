package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Categories;
import com.myapp.money_planner.models.Users;
import com.myapp.money_planner.services.CategoriesService;
import com.myapp.money_planner.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriesController {
    private static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    private final CategoriesService categoriesService;
    private final UsersService usersService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService, UsersService usersService) {
        this.categoriesService = categoriesService;
        this.usersService = usersService;
    }

    public void createDefaultCategories(Users user) {
        String[] defaultCategories = {
                "Food", "Transport", "Entertainment", "Bills",
                "Shopping", "Health", "Education", "Other"
        };

        for (String categoryName : defaultCategories) {
            Categories category = new Categories();
            category.setUser(user);
            category.setCategoryName(categoryName);
            categoriesService.createCategory(category);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCategoriesByUser(@PathVariable Long userId) {
        try {
            logger.info("Received request to get categories for user: {}", userId);

            if (usersService.getUserById(userId).isEmpty()) {
                logger.error("User not found with ID: {}", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new HashMap<String, String>() {
                            {
                                put("error", "User not found");
                            }
                        });
            }

            List<Categories> categories = categoriesService.getCategoriesByUser(userId);
            logger.info("Retrieved {} categories for user {}", categories.size(), userId);
            logger.debug("Categories: {}", categories);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Error getting categories for user {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HashMap<String, String>() {
                        {
                            put("error", "Error retrieving categories: " + e.getMessage());
                        }
                    });
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody Categories category) {
        try {
            logger.info("Creating category: {}", category);
            if (category.getUser() == null || category.getUser().getUserId() == null) {
                logger.error("User information is missing in the request");
                return ResponseEntity.badRequest()
                        .body(new HashMap<String, String>() {
                            {
                                put("error", "User information is required");
                            }
                        });
            }

            Long userId = category.getUser().getUserId();
            if (usersService.getUserById(userId).isEmpty()) {
                logger.error("User not found with ID: {}", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new HashMap<String, String>() {
                            {
                                put("error", "User not found");
                            }
                        });
            }

            Categories newCategory = categoriesService.createCategory(category);
            logger.info("Created category: {}", newCategory);
            return ResponseEntity.ok(newCategory);
        } catch (Exception e) {
            logger.error("Error creating category: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new HashMap<String, String>() {
                        {
                            put("error", "Error creating category: " + e.getMessage());
                        }
                    });
        }
    }

    @PutMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<Categories> updateCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
            @RequestBody Categories category) {
        try {
            Categories updatedCategory = categoriesService.updateCategory(userId, categoryId, category);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long userId, @PathVariable Long categoryId) {
        boolean deleted = categoriesService.deleteCategory(userId, categoryId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<List<Categories>> getLatestCategories(@PathVariable Long userId) {
        try {
            logger.info("Fetching latest categories for user: {}", userId);
            List<Categories> categories = categoriesService.getLatestCategories(userId);
            logger.info("Found {} latest categories", categories.size());
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Error fetching latest categories: {}", e.getMessage());
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
}
