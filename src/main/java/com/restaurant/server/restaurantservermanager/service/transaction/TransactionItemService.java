package com.restaurant.server.restaurantservermanager.service.transaction;

import com.restaurant.server.restaurantservermanager.model.Transaction;
import com.restaurant.server.restaurantservermanager.model.TransactionItem;

import java.util.List;

public interface TransactionItemService {
    TransactionItem saveItem(TransactionItem item);
    List<TransactionItem> saveItems(List<TransactionItem> items);
    List<TransactionItem> getOrderedFoods(Transaction transaction);
    TransactionItem getActiveTransactionItemById( Transaction transaction , Integer id);
}
