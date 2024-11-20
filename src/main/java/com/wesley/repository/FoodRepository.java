package com.wesley.repository;

import com.wesley.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query("SELECT f FROM Food f JOIN FETCH f.foodCategory fc JOIN FETCH fc.restaurant WHERE f.restaurant.id = :restaurantId")
    List<Food> findByRestaurantId(Long restaurantId);




    @Query("SELECT f FROM Food f WHERE lower(f.name) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(f.foodCategory) LIKE lower(concat('%', :keyword, '%'))")
    List<Food> searchFood(@Param("keyword") String keyword);


}
