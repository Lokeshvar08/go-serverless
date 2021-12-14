package com.restaurant.server.restaurantservermanager.service;

import com.restaurant.server.restaurantservermanager.model.Customer;
import com.restaurant.server.restaurantservermanager.model.Dine;
import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.Transaction;
import com.restaurant.server.restaurantservermanager.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction getOrCreateTransaction(Customer customer, Dine dine) {
        Transaction transaction = transactionRepository.getTransactionByDineAndCustomerAndStatus(
                dine,
                customer,
                true
        );
        if( transaction != null ){
            return transaction;
        }
        transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setDine(dine);
        transaction.setRestaurant(dine.getRestaurant());
        transaction.setStatus(true);
        transaction = saveTransaction(transaction);
        return transaction;
    }

    public Transaction getTransactionByKitchen(Integer id, Restaurant restaurant, Boolean status ) {
        return transactionRepository.getTransactionByIdAndRestaurantAndStatus(id, restaurant, status);
    }

    public List<Transaction> getTransactionsByKitchen(Restaurant restaurant, Boolean status) {
        return transactionRepository.getTransactionsByRestaurantAndStatus(restaurant, status);
    }
}
