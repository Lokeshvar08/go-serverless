package com.restaurant.server.restaurantservermanager.service.forms.user;

public class AuthenticatedUserClient {
    private Integer id;
    private String username;
    private String name;
    private String role;
    private String restaurant;

    public AuthenticatedUserClient(Integer id, String username, String name, String role, String restaurant) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
        this.restaurant = restaurant;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }
}
