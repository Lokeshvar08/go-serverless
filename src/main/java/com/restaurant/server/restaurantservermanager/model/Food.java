package com.restaurant.server.restaurantservermanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id", nullable = false)
    private Integer id;

    @Column( name = "name", nullable = false, length = 64)
    private String name;

    @Column( name = "price", nullable = false)
    private Double price;

    @Column( name = "default_food", nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean default_food;

    @Column( name = "status", nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean status;

    @ManyToOne()
    @JoinColumn(name="restaurant", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    @ManyToMany( mappedBy = "foods" )
    @JsonManagedReference
    private List<Category> categories;

    @OneToMany( mappedBy = "food")
    @JsonBackReference
    private List<TransactionItem> transactionItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDefault_food() {
        return default_food;
    }

    public void setDefault_food(Boolean default_food) {
        this.default_food = default_food;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<TransactionItem> getTransactionItem() {
        return transactionItem;
    }

    public void setTransactionItem(List<TransactionItem> transactionItem) {
        this.transactionItem = transactionItem;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", default_food=" + default_food +
                ", status=" + status +
                ", restaurant=" + restaurant +
                ", categories=" + categories +
                ", transactionItem=" + transactionItem +
                '}';
    }
}