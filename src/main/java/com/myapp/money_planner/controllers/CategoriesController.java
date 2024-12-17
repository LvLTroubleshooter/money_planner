package com.myapp.money_planner.controllers;

import com.myapp.money_planner.models.Categories;
import com.myapp.money_planner.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriesController {

    private final CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Categories>> getCategoriesByUser(@PathVariable Long userId) {
        List<Categories> categories = categoriesService.getCategoriesByUser(userId);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/create")
    public ResponseEntity<Categories> createCategory(@RequestBody Categories category) {
        Categories newCategory = categoriesService.createCategory(category);
        return ResponseEntity.ok(newCategory);
    }
}
