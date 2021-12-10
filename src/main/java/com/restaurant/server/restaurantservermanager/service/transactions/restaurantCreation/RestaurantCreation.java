package com.restaurant.server.restaurantservermanager.service.transactions.restaurantCreation;


public interface RestaurantCreation {
    void createRestaurant(
            String username,
            String password,
            String name,
            String phone,
            String restaurant_name,
            String restaurant_branch,
            String restaurant_city
    ) throws Exception;
}
