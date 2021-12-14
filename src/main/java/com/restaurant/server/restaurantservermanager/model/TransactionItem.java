package com.restaurant.server.restaurantservermanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "transaction_item")
public class TransactionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name="food")
    @JsonManagedReference
    private Food food;

    @Column( name = "comment", nullable = true)
    private String comment;

    @Column( name = "quantity", nullable = false)
    private Integer quantity;

    @Enumerated( EnumType.STRING)
    @Column( name = "status", length = 15)
    private Status status;

    @ManyToOne()
    @JoinColumn( name="transaction")
    @JsonBackReference
    private Transaction transaction;



    public enum Status {
        NOT_ORDERED, ORDERED, BEING_PREPARED, SOON, HAPPY_MEAL
    }


    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "TransactionItem{" +
                "id=" + id +
                ", food=" + food +
                ", comment='" + comment + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                ", transaction=" + transaction +
                '}';
    }
}