package com.restaurant.server.restaurantservermanager.service.transaction;

import com.restaurant.server.restaurantservermanager.model.*;
import com.restaurant.server.restaurantservermanager.repository.TransactionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionItemServiceImpl implements TransactionItemService{

    @Autowired
    private TransactionItemRepository transactionItemRepository;

    public TransactionItemRepository getTransactionItemRepository() {
        return transactionItemRepository;
    }

    public void setTransactionItemRepository(TransactionItemRepository transactionItemRepository) {
        this.transactionItemRepository = transactionItemRepository;
    }

    @Override
    public TransactionItem saveItem(TransactionItem item) {
        return transactionItemRepository.save(item);
    }

    @Override
    public List<TransactionItem> saveItems(List<TransactionItem> items) {
        return transactionItemRepository.saveAll(items);
    }

    @Override
    public List<TransactionItem> getOrderedFoods(Transaction transaction) {
        return transactionItemRepository.getTransactionItemsByTransaction(transaction);
    }

    @Override
    public TransactionItem getActiveTransactionItemById( Transaction transaction ,
                                                         Integer id) {
        return transactionItemRepository.getTransactionItemByTransactionAndId(
                transaction,
                id);
    }
}
