package com.wesley.repository;

import com.wesley.model.IngredientCategory;
import com.wesley.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientItemRepository extends JpaRepository<IngredientsItem, Long> {

        List<IngredientsItem> findByRestaurantId(Long restaurantId);
}
