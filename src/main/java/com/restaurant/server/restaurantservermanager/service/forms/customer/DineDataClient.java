package com.restaurant.server.restaurantservermanager.service.forms.customer;

public class DineDataClient {
    private Integer id;
    private Integer number;
    private Integer restaurantId;
    private String restaurantName;
    private String customer;
    private Integer transactionId;
    private String role;

    public DineDataClient(Integer id, Integer number,
                          Integer restaurantId, String restaurantName,
                          String customer) {
        this.id = id;
        this.number = number;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.customer = customer;
        this.role = "ROLE_CUSTOMER";
    }

    public DineDataClient(Integer id, Integer number, Integer restaurantId, String restaurantName) {
        this.id = id;
        this.number = number;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.role = "ROLE_CUSTOMER";
    }



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

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
