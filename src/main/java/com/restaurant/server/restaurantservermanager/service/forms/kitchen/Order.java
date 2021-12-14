package com.restaurant.server.restaurantservermanager.service.forms.kitchen;


import com.restaurant.server.restaurantservermanager.model.TransactionItem;

public class Order {
    private Integer transaction;
    private Integer transactionItem;
    private String food;
    private Integer quantity;
    private String comment;
    private Integer dineNumber;
    private Integer dineId;
    private TransactionItem.Status status;

    public Order(Integer transaction,
                 Integer transactionItem,
                 String food,
                 Integer quantity,
                 String comment,
                 Integer dineNumber,
                 Integer dineId,
                 TransactionItem.Status status) {
        this.transaction = transaction;
        this.transactionItem = transactionItem;
        this.food = food;
        this.quantity = quantity;
        this.comment = comment;
        this.dineNumber = dineNumber;
        this.dineId = dineId;
        this.status = status;
    }

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

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getDineNumber() {
        return dineNumber;
    }

    public void setDineNumber(Integer dineNumber) {
        this.dineNumber = dineNumber;
    }

    public Integer getDineId() {
        return dineId;
    }

    public void setDineId(Integer dineId) {
        this.dineId = dineId;
    }

    public TransactionItem.Status getStatus() {
        return status;
    }

    public void setStatus(TransactionItem.Status status) {
        this.status = status;
    }
}
