package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    List<Categories> findByUser_UserId(Long userId);
}
