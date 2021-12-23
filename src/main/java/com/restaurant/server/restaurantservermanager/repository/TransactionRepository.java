package com.restaurant.server.restaurantservermanager.repository;

import com.restaurant.server.restaurantservermanager.model.Customer;
import com.restaurant.server.restaurantservermanager.model.Dine;
import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction getTransactionByDineAndCustomerAndStatus(Dine dine, Customer customer, Boolean status);
    Transaction getTransactionByIdAndRestaurantAndStatus(Integer id, Restaurant restaurant, Boolean status);
    List<Transaction> getTransactionsByRestaurantAndStatus(Restaurant restaurant, Boolean status );
    List<Transaction> getTransactionsByUpdateDateAfterAndUpdateDateBeforeAndRestaurant(
            Date from,
            Date to,
            Restaurant restaurant);
}