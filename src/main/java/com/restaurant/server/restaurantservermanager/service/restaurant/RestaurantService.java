package com.restaurant.server.restaurantservermanager.service.restaurant;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.User;

public interface RestaurantService {
    void createRestaurant(Restaurant restaurant);
    void updateRestaurantWithUser(Restaurant restaurant, User user);
    Integer generateRandomIdRestaurant(Restaurant restaurant);
}
