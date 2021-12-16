package com.restaurant.server.restaurantservermanager.service.transactions.employeeCreation;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.service.restaurant.RestaurantService;
import com.restaurant.server.restaurantservermanager.service.user.UserService;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeCreationTransactionImpl implements EmployeeCreationTransaction{

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public RestaurantService getRestaurantService() {
        return restaurantService;
    }

    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = { ServiceErrorHandler.class })
    @Override
    public void createUser(User user) throws Exception {
        try {
            userService.createUser(user);
        } catch (Exception e) {
            throw new ServiceErrorHandler("unable to create employee");
        }
    }

    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = { ServiceErrorHandler.class })
    @Override
    public void linkUserWithRestaurant(User user, Restaurant restaurant) throws Exception {
        try{
            userService.updateUserWithRestaurant(user, restaurant);
            restaurantService.updateRestaurantWithUser(restaurant, user);
        } catch (Exception e) {
            throw new ServiceErrorHandler("unable to link employee with restaurant");
        }
    }
}
