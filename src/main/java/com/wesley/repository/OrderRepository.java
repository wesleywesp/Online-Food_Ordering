package com.wesley.repository;

import com.wesley.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order>findByCustomerId(Long customerId);

    public List<Order> findByRestaurantId(Long restaurantId);
}
