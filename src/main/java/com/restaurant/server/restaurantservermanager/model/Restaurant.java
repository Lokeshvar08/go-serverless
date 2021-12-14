package com.restaurant.server.restaurantservermanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id", nullable = false)
    private Integer id;

    @Column( name = "name", nullable = false, length = 64)
    private String name;

    @Column( name = "branch", nullable = false, length = 64)
    private String branch;

    @Column( name = "city", nullable = false, length = 64)
    private String city;

    @Column( name = "status", nullable = false)
    private Boolean status;

    @Column( name = "random_code", nullable = true)
    private Integer random_code;

    @OneToMany( mappedBy = "restaurant")
    @JsonBackReference
    private List<User> employees;

    @OneToMany( mappedBy = "restaurant")
    @JsonManagedReference
    private List<Dine> dines;

    @OneToMany( mappedBy = "restaurant")
    @JsonManagedReference
    private List<Food> foods;

    @OneToMany( mappedBy = "restaurant")
    @JsonBackReference
    private List<Category> categories;

    @OneToMany( mappedBy = "restaurant")
    @JsonManagedReference
    private List<Transaction> transactions;


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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getRandom_code() {
        return random_code;
    }

    public void setRandom_code(Integer random_code) {
        this.random_code = random_code;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    public List<Dine> getDines() {
        return dines;
    }

    public void setDines(List<Dine> dines) {
        this.dines = dines;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", branch='" + branch + '\'' +
                ", city='" + city + '\'' +
                ", status=" + status +
                ", random_code=" + random_code +
                ", employees=" + employees +
                ", dines=" + dines +
                ", foods=" + foods +
                ", categories=" + categories +
                ", transactions=" + transactions +
                '}';
    }
}