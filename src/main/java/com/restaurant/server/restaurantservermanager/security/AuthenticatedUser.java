package com.restaurant.server.restaurantservermanager.security;

import com.restaurant.server.restaurantservermanager.model.User;

public interface AuthenticatedUser {
    User getAuthenticatedUserObject() throws Exception;
}
