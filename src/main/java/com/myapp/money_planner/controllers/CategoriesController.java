package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Categories;
import com.myapp.money_planner.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private final CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<Categories> createCategory(@RequestBody Categories category) {
        Categories createdCategory = categoriesService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    // Read by ID
    @GetMapping("/{categoryId}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Long categoryId) {
        Optional<Categories> category = categoriesService.getCategoryById(categoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Categories> getCategoryByUserId(@PathVariable Long userId) {
        Optional<Categories> category = categoriesService.getCategoryByUserId(userId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read by Name
    @GetMapping("/name/{categoryName}")
    public ResponseEntity<Categories> getCategoryByName(@PathVariable String categoryName) {
        Optional<Categories> category = categoriesService.getCategoryByName(categoryName);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{categoryId}")
    public ResponseEntity<Categories> updateCategory(@PathVariable Long categoryId, @RequestBody Categories category) {
        Categories updatedCategory = categoriesService.updateCategory(categoryId, category);
        return updatedCategory != null ? ResponseEntity.ok(updatedCategory) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        boolean deleted = categoriesService.deleteCategory(categoryId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
