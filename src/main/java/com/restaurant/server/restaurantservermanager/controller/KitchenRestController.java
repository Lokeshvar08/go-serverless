package com.restaurant.server.restaurantservermanager.controller;

import com.restaurant.server.restaurantservermanager.controller.response.ResponseGenericObject;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseStatus;
import com.restaurant.server.restaurantservermanager.model.Food;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.security.AuthenticatedUser;
import com.restaurant.server.restaurantservermanager.service.FoodService;
import com.restaurant.server.restaurantservermanager.service.forms.food.FoodCreate;
import com.restaurant.server.restaurantservermanager.service.forms.food.FoodUpdate;
import com.restaurant.server.restaurantservermanager.service.forms.food.FoodUpdateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kitchen")
public class KitchenRestController {

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private FoodService foodService;

    public AuthenticatedUser getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public FoodService getFoodService() {
        return foodService;
    }

    public void setFoodService(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/food-create")
    public ResponseStatus createFood(@RequestBody FoodCreate foodForm) {
        User auth;
        String error = "not created";
        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            if( auth.getRole() == User.Role.ROLE_KITCHEN ) {
                Food food = new Food();
                food.setName(foodForm.getName());
                food.setPrice(Double.parseDouble(foodForm.getPrice()));
                food.setDefault_food(false);
                food.setStatus(false);
                food.setRestaurant(auth.getRestaurant());
                Food newFood = foodService.createFood(food);
                return new ResponseGenericObject<>(newFood, true, "creation successful");
            }
        } catch ( Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus(false, error);
    }

    @PutMapping("/food-update")
    public ResponseStatus updateFood(@RequestBody FoodUpdate foodUpdate) {
        User auth;
        String error = "not updated";
        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            if(  auth.getRole() == User.Role.ROLE_KITCHEN ) {
                Food food = foodService.getFoodByIdAndRestaurant( foodUpdate.getId(), auth.getRestaurant());
                double foodPrice = Double.parseDouble(foodUpdate.getPrice());
                if( foodPrice > 0 ) {
                    food.setPrice(foodPrice);
                    Food newFood = foodService.updateFood(food);
                    return new ResponseGenericObject<>( newFood, true, "updated successfully");
                }
            }
        } catch ( Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus( false, error);
    }

    @PutMapping("/food-update-status")
    public ResponseStatus updateFoodStatus(@RequestBody FoodUpdateStatus foodUpdate ) {
        User auth;
        String error = "status not updated";
        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            if( auth.getRole() == User.Role.ROLE_KITCHEN ) {
                Food food = foodService.getFoodByIdAndRestaurant( foodUpdate.getId(), auth.getRestaurant());
                food.setStatus(foodUpdate.getStatus());
                Food newFood = foodService.updateFood(food);
                return new ResponseGenericObject<>(newFood, true, "updated successfully");
            }
        } catch (Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus( false, error);
    }

    @PutMapping("/food-update-default")
    public ResponseStatus updateFoodDefault(@RequestBody FoodUpdateStatus foodUpdate ) {
        User auth;
        String error = "default not updated";
        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            if( auth.getRole() == User.Role.ROLE_KITCHEN ) {
                Food food = foodService.getFoodByIdAndRestaurant( foodUpdate.getId(), auth.getRestaurant());
                food.setDefault_food(foodUpdate.getStatus());
                Food newFood = foodService.updateFood(food);
                return new ResponseGenericObject<>(newFood, true, "updated successfully");
            }
        } catch (Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus( false, error);
    }

    @DeleteMapping("/food/delete/{foodId}")
    public ResponseStatus deleteFood( @PathVariable String foodId) {
        User auth;
        String error = "food deletion failed";
        try{
            auth = authenticatedUser.getAuthenticatedUserObject();
            if( auth.getRole() == User.Role.ROLE_KITCHEN ) {
                foodService.deleteFoodByKitchen( Integer.parseInt(foodId), auth.getRestaurant() );
                return new ResponseStatus( true, "food deleted successfully");
            }
        } catch ( Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus( false, error);
    }
}
