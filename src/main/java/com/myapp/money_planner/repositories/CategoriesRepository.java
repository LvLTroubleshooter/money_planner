package com.myapp.money_planner.repositories;

import com.myapp.money_planner.models.Categories;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    @NonNull
    Optional<Categories> findByCategoryName(@NonNull String categoryName);

    @NonNull
    Optional<Categories> findById(@NonNull Long categoryId);
}
