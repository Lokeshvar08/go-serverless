package com.restaurant.server.restaurantservermanager.repository;

import com.restaurant.server.restaurantservermanager.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}