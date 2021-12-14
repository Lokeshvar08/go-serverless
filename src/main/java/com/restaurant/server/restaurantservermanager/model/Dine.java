package com.restaurant.server.restaurantservermanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "dine")
public class Dine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id", nullable = false)
    private Integer id;

    @Column( name = "number", nullable = false)
    private Integer number;

    @Column( name = "status", nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean status;

    @Column( name = "otp", nullable = true)
    private Integer otp;

    @ManyToOne()
    @JoinColumn( name = "restaurant", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    @OneToMany( mappedBy = "dine")
    @JsonBackReference
    private List<Transaction> transactions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Dine{" +
                "id=" + id +
                ", number=" + number +
                ", status=" + status +
                ", otp=" + otp +
                ", restaurant=" + restaurant +
                ", transactions=" + transactions +
                '}';
    }
}