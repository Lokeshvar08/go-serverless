package com.restaurant.server.restaurantservermanager.repository;

import com.restaurant.server.restaurantservermanager.model.Food;
import com.restaurant.server.restaurantservermanager.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    Food getFoodByIdAndRestaurant( Integer id, Restaurant restaurant);

    List<Food> getFoodsByRestaurantAndStatus( Restaurant restaurant, Boolean status);

    List<Food> getFoodsByRestaurant(Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Food f WHERE f.id=?1 AND f.restaurant=?2")
    Integer deleteFoodByKitchen(Integer id, Restaurant restaurant);
}