package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    List<Categories> findByUser_UserId(Long userId);

    Optional<Categories> findByCategoryIdAndUser_UserId(Long categoryId, Long userId);

    List<Categories> findTop3ByUser_UserIdOrderByCreatedAtDesc(Long userId);

    Optional<Categories> findByUserUserIdAndCategoryId(Long userId, Long categoryId);
}
