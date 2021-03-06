package com.restaurant.server.restaurantservermanager.controller;

import com.restaurant.server.restaurantservermanager.controller.response.ResponseGenericListObject;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseGenericObject;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseStatus;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.security.AuthenticatedUser;
import com.restaurant.server.restaurantservermanager.service.chart.ChartService;
import com.restaurant.server.restaurantservermanager.service.user.UserService;
import com.restaurant.server.restaurantservermanager.service.forms.user.UpdateUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private ChartService chartService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public AuthenticatedUser getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public ChartService getChartService() {
        return chartService;
    }

    public void setChartService(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/employees")
    public ResponseStatus getAllEmployees() {
        User user;
        String error = "";
        try {
            user = authenticatedUser.getAuthenticatedUserObject();
            if( user != null) {
                return new ResponseGenericListObject<>(
                        userService.getUsersByRestaurantAdmin(user.getRestaurant()),
                        true,
                        "success"
                );
            }
        } catch (Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseGenericListObject<>(false, error);
    }

    @PutMapping( "/employee")
    public ResponseStatus updateEmployee(@RequestBody UpdateUserRole userUpdate) {
        User auth;
        String error;
        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            User user = userService.findUserByIdAndRestaurant(userUpdate.getId(), auth.getRestaurant());
            if( user != null && userUpdate.getRole() != User.Role.ROLE_ADMIN) {
                user.setRole(userUpdate.getRole());
                userService.updateUser(user);
                return new ResponseGenericObject<>(user, true, "successfully updated");
            } else {
                return new ResponseStatus(false, "cannot be updated to admin or user not available");
            }

        } catch( Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseGenericObject<>( false, error);
    }

    @DeleteMapping("/employee/delete/{user}")
    public ResponseStatus deleteEmployee( @PathVariable String user) {
        User auth;
        String error;
        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            userService.deleteUserByIdAndRestaurantByAdmin(Integer.parseInt(user), auth.getRestaurant());
            return new ResponseStatus(true, "success");
        } catch( Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus(false, error);
    }

    @GetMapping("/chart/food/{from}/{to}")
    public ResponseStatus getFoodChart(@PathVariable String from,
                                                        @PathVariable String to) throws ParseException {
        String error = "";
        User auth;
        try {
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            auth = authenticatedUser.getAuthenticatedUserObject();
            return new ResponseGenericObject<>(
                    chartService.getFoodChart(date.parse(from), date.parse(to), auth.getRestaurant()) ,
                    true,
                    "success");
        } catch (Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus(false, error);
    }
}
