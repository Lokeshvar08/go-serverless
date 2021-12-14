package com.restaurant.server.restaurantservermanager.service.forms.kitchen;

import com.restaurant.server.restaurantservermanager.model.TransactionItem;

public class UpdateTransactionItemStatus {
    private Integer transaction;
    private Integer transactionItem;
    private TransactionItem.Status status;

    public Integer getTransaction() {
        return transaction;
    }

    public void setTransaction(Integer transaction) {
        this.transaction = transaction;
    }

    public Integer getTransactionItem() {
        return transactionItem;
    }

    public void setTransactionItem(Integer transactionItem) {
        this.transactionItem = transactionItem;
    }

    public TransactionItem.Status getStatus() {
        return status;
    }

    public void setStatus(TransactionItem.Status status) {
        this.status = status;
    }
}
