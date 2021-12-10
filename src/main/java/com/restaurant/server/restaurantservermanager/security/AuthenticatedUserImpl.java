package com.restaurant.server.restaurantservermanager.security;

import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserImpl implements AuthenticatedUser {


    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getAuthenticatedUserObject() throws Exception {
        String user = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();
        return userService.findUserByUsername(user);
    }
}
