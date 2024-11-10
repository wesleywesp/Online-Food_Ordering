package com.wesley.repository;

import com.wesley.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarItemRepository extends JpaRepository<CartItem, Long> {
}
