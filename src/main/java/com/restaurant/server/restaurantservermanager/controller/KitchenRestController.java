package com.restaurant.server.restaurantservermanager.controller;

import com.restaurant.server.restaurantservermanager.controller.response.ResponseGenericListObject;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseGenericObject;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseStatus;
import com.restaurant.server.restaurantservermanager.model.Food;
import com.restaurant.server.restaurantservermanager.model.Transaction;
import com.restaurant.server.restaurantservermanager.model.TransactionItem;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.security.AuthenticatedUser;
import com.restaurant.server.restaurantservermanager.service.FoodService;
import com.restaurant.server.restaurantservermanager.service.TransactionItemService;
import com.restaurant.server.restaurantservermanager.service.TransactionService;
import com.restaurant.server.restaurantservermanager.service.forms.food.FoodCreate;
import com.restaurant.server.restaurantservermanager.service.forms.food.FoodUpdate;
import com.restaurant.server.restaurantservermanager.service.forms.food.FoodUpdateStatus;
import com.restaurant.server.restaurantservermanager.service.forms.kitchen.Order;
import com.restaurant.server.restaurantservermanager.service.forms.kitchen.UpdateTransactionItemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/kitchen")
public class KitchenRestController {

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private FoodService foodService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionItemService transactionItemService;

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

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionItemService getTransactionItemService() {
        return transactionItemService;
    }

    public void setTransactionItemService(TransactionItemService transactionItemService) {
        this.transactionItemService = transactionItemService;
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

    @GetMapping("/orders")
    public ResponseStatus getOrdersOfKitchen() {
        User auth;
        String error = "unable to get the orders";
        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            if( auth.getRole() == User.Role.ROLE_KITCHEN ) {
                List<Transaction> transactions = transactionService.getTransactionsByKitchen(
                        auth.getRestaurant() ,
                        true
                );
                List<Order> orders = new ArrayList<>();
                transactions.forEach( transaction -> {
                    transaction.getFoodItems().forEach( foodItem -> {
                        if( foodItem.getStatus() != TransactionItem.Status.HAPPY_MEAL) {
                            orders.add(
                                    new Order(
                                            transaction.getId(),
                                            foodItem.getId(),
                                            foodItem.getFood().getName(),
                                            foodItem.getQuantity(),
                                            foodItem.getComment(),
                                            transaction.getDine().getNumber(),
                                            transaction.getDine().getId(),
                                            foodItem.getStatus()
                                    )
                            );
                        }
                    });
                });
                return new ResponseGenericListObject<>( orders, true, "success");
            }
        } catch ( Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus(false, error);
    }

    @PutMapping("/update-transaction-item")
    public ResponseStatus updateTransactionItemStatus(@RequestBody UpdateTransactionItemStatus update) {
        User auth;
        String error = "transaction update status failed";
        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            if( auth.getRole() == User.Role.ROLE_KITCHEN ) {
                if(
                        update.getStatus() == TransactionItem.Status.BEING_PREPARED
                        || update.getStatus() == TransactionItem.Status.HAPPY_MEAL
                ) {
                    Transaction transaction = transactionService.getTransactionByKitchen(
                            update.getTransaction(),
                            auth.getRestaurant(),
                            true
                    );
                    TransactionItem transactionItem = transactionItemService.getActiveTransactionItemById(
                            transaction,
                            update.getTransactionItem()
                    );
                    transactionItem.setStatus(update.getStatus());
                    transactionItemService.saveItem(transactionItem);
                    return new ResponseGenericObject<>( transactionItem, true, "success");
                }
            }
        } catch ( Exception e ) {
            error = String.valueOf(e);
        }
        return new ResponseStatus( false, error);
    }
}
