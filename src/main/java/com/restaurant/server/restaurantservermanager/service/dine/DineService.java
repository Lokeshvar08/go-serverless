package com.restaurant.server.restaurantservermanager.service.dine;

import com.restaurant.server.restaurantservermanager.model.Dine;
import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;

import java.util.List;

public interface DineService {
    void createDineInRestaurant(Restaurant restaurant);
    List<Dine> getAllDinesOfRestaurant(Restaurant restaurant);
    void deleteDineInRestaurant(Integer id, Restaurant restaurant) throws ServiceErrorHandler;
    Integer getDineCountOfRestaurant(Restaurant restaurant);
    Integer getActiveDineCountOfRestaurant( Restaurant restaurant);
    Dine getDineByNumberAndRestaurant(Integer number, Restaurant restaurant);
    void updateDine( Dine dine);
}
