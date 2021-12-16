package com.restaurant.server.restaurantservermanager.service.food;

import com.restaurant.server.restaurantservermanager.model.Food;
import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.repository.FoodRepository;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public FoodRepository getFoodRepository() {
        return foodRepository;
    }

    public void setFoodRepository(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public Food getFoodByIdAndRestaurant(Integer id, Restaurant restaurant) {
        return foodRepository.getFoodByIdAndRestaurant( id, restaurant);
    }

    @Override
    public List<Food> getAvailableFoodsOfRestaurant(Restaurant restaurant) {
        return foodRepository.getFoodsByRestaurantAndStatus(restaurant, true);
    }

    @Override
    public List<Food> getFoodsOfRestaurant(Restaurant restaurant) {
        return foodRepository.getFoodsByRestaurant(restaurant);
    }

    @Override
    public void deleteFoodByKitchen( Integer id, Restaurant restaurant) throws ServiceErrorHandler {
        int n = foodRepository.deleteFoodByKitchen( id, restaurant);
        if( n == 1) {
            return;
        }
        throw new ServiceErrorHandler("food not found/deleted");
    }
}
