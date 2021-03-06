package com.restaurant.server.restaurantservermanager.service.user;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.repository.UserRepository;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //find user

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public User findUserByIdAndRestaurant(Integer id, Restaurant restaurant) {
        return userRepository.getUserByIdAndRestaurant(id, restaurant);
    }

    //create user

    @Override
    public void createAdminUser(User user) {
        user.setRole(User.Role.ROLE_ADMIN);
        userRepository.save(user);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    //update user

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUserWithRestaurant(User user, Restaurant restaurant) {
        user.setRestaurant(restaurant);
        userRepository.save(user);
    }

    //get users by restaurant

    @Override
    public List<User> getUsersByRestaurantAdmin( Restaurant restaurant) {
        return userRepository.queryUsersByRestaurantAndRoleIsNot(restaurant, User.Role.ROLE_ADMIN);
    }

    @Override
    public List<User> getUsersByRestaurantManager( Restaurant restaurant) {
        return userRepository.queryUsersByRestaurantAndRoleIsNotAndRoleIsNot(
                restaurant,
                User.Role.ROLE_ADMIN,
                User.Role.ROLE_MANAGER);
    }

    @Override
    public void deleteUserByIdAndRestaurantByAdmin( Integer userId, Restaurant restaurant) throws ServiceErrorHandler {
        int n = userRepository.deleteUserByAdmin( userId, restaurant, User.Role.ROLE_ADMIN);
        if( n ==  1 ) {
            return;
        }
        throw new ServiceErrorHandler("cannot delete user!");
    }

    @Override
    public void deleteUserByIdAndRestaurantByManager( Integer userId, Restaurant restaurant) throws ServiceErrorHandler {
        int n = userRepository.deleteUserByManager( userId, restaurant, User.Role.ROLE_ADMIN, User.Role.ROLE_MANAGER);
        if( n == 1 ) {
            return;
        }
        throw new ServiceErrorHandler("cannot delete user!");
    }




}
