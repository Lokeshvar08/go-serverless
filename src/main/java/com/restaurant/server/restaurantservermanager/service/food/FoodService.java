package com.restaurant.server.restaurantservermanager.service.food;

import com.restaurant.server.restaurantservermanager.model.Food;
import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;

import java.util.List;

public interface FoodService {
    Food createFood(Food food);
    Food updateFood(Food food);
    Food getFoodByIdAndRestaurant(Integer id, Restaurant restaurant);
    List<Food> getAvailableFoodsOfRestaurant(Restaurant restaurant);
    List<Food> getNotAvailableFoodsOfRestaurant(Restaurant restaurant);
    void deleteFoodByKitchen( Integer id, Restaurant restaurant) throws ServiceErrorHandler;
}
