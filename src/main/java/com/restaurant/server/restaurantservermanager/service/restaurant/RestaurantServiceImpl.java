package com.restaurant.server.restaurantservermanager.service.restaurant;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantRepository getRestaurantRepository() {
        return restaurantRepository;
    }

    public void setRestaurantRepository(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void createRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    @Override
    public void updateRestaurantWithUser(Restaurant restaurant, User user) {
        List<User> employees = restaurant.getEmployees();
        if( employees == null ) {
            employees = new ArrayList<>();
        }
        employees.add(user);
        restaurant.setEmployees(employees);
        restaurantRepository.save(restaurant);
    }

    @Override
    public Integer generateRandomIdRestaurant(Restaurant restaurant){
        Random random = new Random();
        Integer code = Integer.parseInt(String.format("%04d", random.nextInt(10000)));
        restaurant.setRandom_code(code);
        restaurantRepository.save(restaurant);
        return code;
    }
}
