package com.restaurant.server.restaurantservermanager.repository;

import com.restaurant.server.restaurantservermanager.model.Transaction;
import com.restaurant.server.restaurantservermanager.model.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, Integer> {
    List<TransactionItem> getTransactionItemsByTransaction(Transaction transaction);
    TransactionItem getTransactionItemByTransactionAndId(
            Transaction transaction,
            Integer transactionItemId);
}