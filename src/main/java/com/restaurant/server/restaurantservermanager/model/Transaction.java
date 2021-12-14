package com.restaurant.server.restaurantservermanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column( name = "total", nullable = true)
    private Double total;

    @Column( name = "discount", nullable = true)
    private Integer discount;

    @Column( name = "comment")
    private String comment;

    @ManyToOne()
    @JoinColumn( name="restaurant", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    @ManyToOne()
    @JoinColumn( name = "dine", nullable = false)
    @JsonManagedReference
    private Dine dine;

    @ManyToOne()
    @JoinColumn( name = "customer", nullable = false)
    @JsonManagedReference
    private Customer customer;

    @OneToMany( mappedBy = "transaction")
    @JsonManagedReference
    private List<TransactionItem> foodItems;

    @Column( name = "status", nullable = false)
    private Boolean status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column( name= "create_date", updatable = false)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column( name= "update_date")
    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<TransactionItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<TransactionItem> foodItems) {
        this.foodItems = foodItems;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Dine getDine() {
        return dine;
    }

    public void setDine(Dine dine) {
        this.dine = dine;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", total=" + total +
                ", discount=" + discount +
                ", comment='" + comment + '\'' +
                ", restaurant=" + restaurant +
                ", dine=" + dine +
                ", customer=" + customer +
                ", foodItems=" + foodItems +
                ", status=" + status +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}