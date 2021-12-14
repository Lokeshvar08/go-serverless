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
import java.util.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id", nullable = false)
    private Integer id;

    @Column( name = "username", nullable = false, length = 64, unique = true)
    private String username;

    @Column( name = "password", nullable = false, length = 64)
    private String password;

    @Column( name = "name", nullable = false, length = 64)
    private String name;

    @Column( name = "phone", nullable = true, length = 10)
    private String phone;

    @Column( name = "status", nullable = false)
    private Boolean status;

    @Enumerated( EnumType.STRING)
    @Column( name = "role", length = 12)
    private Role role;

    @ManyToOne()
    @JoinColumn(name="restaurant")
    @JsonManagedReference
    private Restaurant restaurant;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column( name= "create_date", updatable = false)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column( name= "update_date")
    private Date updateDate;


    public enum Role {
        ROLE_ADMIN, ROLE_MANAGER, ROLE_KITCHEN, ROLE_CASHIER, ROLE_QUEUE, ROLE_NONE
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", role=" + role +
                ", restaurant=" + restaurant +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}