package com.wesley.repository;

import com.wesley.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByOwnerId(Long ownerId);

    Restaurant findByName(String name);

    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:query,'%'))" +
            "OR lower(r.cusinetypes) LIKE lower(concat('%',:query,'%'))")
    List<Restaurant> finBySearchQuery(String query);

    Restaurant findRestaurantById(Long restaurantId);

    List<Restaurant> findAllByOwnerId(Long userId);
}
