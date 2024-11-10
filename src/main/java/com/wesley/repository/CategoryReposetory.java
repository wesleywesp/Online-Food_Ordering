package com.wesley.repository;

import com.wesley.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryReposetory extends JpaRepository<Category, Long> {
    public List<Category> findByRestaurantId(Long restaurantId);
}
