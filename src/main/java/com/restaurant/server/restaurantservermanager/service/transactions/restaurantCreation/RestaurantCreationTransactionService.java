package com.restaurant.server.restaurantservermanager.service.transactions.restaurantCreation;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.User;

public interface RestaurantCreationTransactionService {
    void createUser(User user) throws Exception;
    void createRestaurant(Restaurant restaurant) throws Exception;
    void linkUserAndRestaurant(User user, Restaurant restaurant) throws Exception;
}
