package com.restaurant.server.restaurantservermanager.service.user;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;

import java.util.List;

public interface UserService {
    User findUserById(int id);
    User findUserByUsername(String username);
    User findUserByIdAndRestaurant(Integer id, Restaurant restaurant);
    void createAdminUser(User user);
    void createUser(User user);
    void updateUser(User user);
    void updateUserWithRestaurant(User user, Restaurant restaurant);
    List<User> getUsersByRestaurantAdmin(Restaurant restaurant);
    List<User> getUsersByRestaurantManager( Restaurant restaurant);
    void deleteUserByIdAndRestaurantByAdmin( Integer userId, Restaurant restaurant) throws ServiceErrorHandler;
    void deleteUserByIdAndRestaurantByManager( Integer userId, Restaurant restaurant) throws ServiceErrorHandler;
}
