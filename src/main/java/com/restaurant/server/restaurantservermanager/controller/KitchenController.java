package com.restaurant.server.restaurantservermanager.controller;

import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.security.AuthenticatedUser;
import com.restaurant.server.restaurantservermanager.service.forms.user.AuthenticatedUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/kitchen")
public class KitchenController {

    @Autowired
    private AuthenticatedUser authenticatedUser;

    public AuthenticatedUser getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    @GetMapping("/manage-food")
    public ModelAndView home(ModelAndView mv) {

        try {
            mv.setViewName("kitchen/manage-food");
            User user = authenticatedUser.getAuthenticatedUserObject();
            mv.addObject("user", new AuthenticatedUserClient(
                    user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getRole().toString(),
                    user.getRestaurant().getName(),
                    user.getRestaurant().getId()
            ));
            return mv;
        }catch (Exception e) {
            mv.setViewName("auth/login");
            mv.addObject("error", "invalid");
            return mv;
        }
    }

    @GetMapping("/orders")
    public ModelAndView getOrder(ModelAndView mv) {
        try {
            mv.setViewName("kitchen/orders");
            User user = authenticatedUser.getAuthenticatedUserObject();
            mv.addObject("user", new AuthenticatedUserClient(
                    user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getRole().toString(),
                    user.getRestaurant().getName(),
                    user.getRestaurant().getId()
            ));
            return mv;
        }catch (Exception e) {
            mv.setViewName("auth/login");
            mv.addObject("error", "invalid");
            return mv;
        }
    }

}
