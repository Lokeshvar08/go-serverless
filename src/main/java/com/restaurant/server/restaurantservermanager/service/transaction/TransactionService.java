package com.restaurant.server.restaurantservermanager.service.transaction;

import com.restaurant.server.restaurantservermanager.model.Customer;
import com.restaurant.server.restaurantservermanager.model.Dine;
import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);
    Transaction getOrCreateTransaction(Customer customer, Dine dine);
    Transaction getTransactionByKitchen(Integer id, Restaurant restaurant, Boolean status );
    List<Transaction> getTransactionsByKitchen(Restaurant restaurant, Boolean status);
}
