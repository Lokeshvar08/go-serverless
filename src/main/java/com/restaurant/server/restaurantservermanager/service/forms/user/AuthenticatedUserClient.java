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
}
