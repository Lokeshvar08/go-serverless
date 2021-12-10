package com.restaurant.server.restaurantservermanager.service.forms.user;

import com.restaurant.server.restaurantservermanager.model.User;

public class UpdateUserRole {
    private Integer id;
    private User.Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
