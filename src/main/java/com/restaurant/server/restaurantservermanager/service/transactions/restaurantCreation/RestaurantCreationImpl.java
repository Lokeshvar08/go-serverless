package com.restaurant.server.restaurantservermanager.service.transactions.restaurantCreation;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RestaurantCreationImpl implements RestaurantCreation {

    @Autowired
    private RestaurantCreationTransactionService restaurantCreationTransactionService;

    public RestaurantCreationTransactionService getTransactionService() {
        return restaurantCreationTransactionService;
    }

    public void setTransactionService(RestaurantCreationTransactionService restaurantCreationTransactionService) {
        this.restaurantCreationTransactionService = restaurantCreationTransactionService;
    }

    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = { ServiceErrorHandler.class })
    @Override
    public void createRestaurant(
            String username,
            String password,
            String name,
            String phone,
            String restaurant_name,
            String restaurant_branch,
            String restaurant_city) throws Exception {

        User user = new User();
        Restaurant restaurant = new Restaurant();

        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder(10).encode(password));
        user.setName(name);
        user.setPhone(phone);
        user.setStatus(true);

        restaurant.setName(restaurant_name);
        restaurant.setBranch(restaurant_branch);
        restaurant.setCity(restaurant_city);
        restaurant.setStatus(false);

        try {
            restaurantCreationTransactionService.createUser(user);
            restaurantCreationTransactionService.createRestaurant(restaurant);
            restaurantCreationTransactionService.linkUserAndRestaurant(user, restaurant);
        } catch (Exception e) {
            throw new ServiceErrorHandler("Error in Transaction of Creation: " + e);
        }
    }
}
