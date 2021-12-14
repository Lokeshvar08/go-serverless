package com.restaurant.server.restaurantservermanager.service;

import com.restaurant.server.restaurantservermanager.model.*;
import com.restaurant.server.restaurantservermanager.repository.TransactionItemRepository;
import com.restaurant.server.restaurantservermanager.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionItemService {

    @Autowired
    private TransactionItemRepository transactionItemRepository;

    public TransactionItemRepository getTransactionItemRepository() {
        return transactionItemRepository;
    }

    public void setTransactionItemRepository(TransactionItemRepository transactionItemRepository) {
        this.transactionItemRepository = transactionItemRepository;
    }

    public TransactionItem saveItem(TransactionItem item) {
        return transactionItemRepository.save(item);
    }

    public List<TransactionItem> saveItems(List<TransactionItem> items) {
        return transactionItemRepository.saveAll(items);
    }

    public List<TransactionItem> getOrderedFoods(Transaction transaction) {
        return transactionItemRepository.getTransactionItemByTransaction(transaction);
    }

    public TransactionItem getActiveTransactionItemById( Transaction transaction ,
                                                         Integer id) {
        return transactionItemRepository.getTransactionItemByTransactionAndId(
                transaction,
                id);
    }
}
