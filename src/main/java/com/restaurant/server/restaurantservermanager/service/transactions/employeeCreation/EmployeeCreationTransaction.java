package com.restaurant.server.restaurantservermanager.service.transactions.employeeCreation;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.User;

public interface EmployeeCreationTransaction {
    void createUser(User user) throws Exception;
    void linkUserWithRestaurant(User user, Restaurant restaurant) throws Exception;
}
