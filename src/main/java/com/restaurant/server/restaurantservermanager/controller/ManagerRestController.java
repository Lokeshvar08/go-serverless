package com.restaurant.server.restaurantservermanager.controller;

import com.restaurant.server.restaurantservermanager.controller.response.ResponseGenericListObject;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseGenericObject;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseGenericVarArgs;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseStatus;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.security.AuthenticatedUser;
import com.restaurant.server.restaurantservermanager.service.DineService;
import com.restaurant.server.restaurantservermanager.service.RestaurantService;
import com.restaurant.server.restaurantservermanager.service.UserService;
import com.restaurant.server.restaurantservermanager.service.forms.user.UpdateUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/manager")
public class ManagerRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private DineService dineService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public DineService getDineService() {
        return dineService;
    }

    public void setDineService(DineService dineService) {
        this.dineService = dineService;
    }

    public AuthenticatedUser getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    @GetMapping("/employees")
    public ResponseStatus getAllEmployees() {
        User user;
        String error = "";
        try {
            user = authenticatedUser.getAuthenticatedUserObject();
            if( user != null) {
                return new ResponseGenericListObject<>(
                        userService.getUsersByRestaurantManager(user.getRestaurant()),
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
            if( user != null &&
                    user.getRole() != User.Role.ROLE_ADMIN &&
                    user.getRole() != User.Role.ROLE_MANAGER &&
                    userUpdate.getRole() != User.Role.ROLE_ADMIN &&
                    userUpdate.getRole() != User.Role.ROLE_MANAGER ) {
                user.setRole(userUpdate.getRole());
                userService.updateUser(user);
                return new ResponseGenericObject<>(user, true, "successfully updated");
            } else {
                return new ResponseStatus(
                        false,
                        "cannot be updated or update admin/manager to admin/manager or user not available");
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
            userService.deleteUserByIdAndRestaurantByManager(Integer.parseInt(user), auth.getRestaurant());
            return new ResponseStatus(true, "success");
        } catch( Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus(false, error);
    }

    @PostMapping("/dine/create")
    public ResponseStatus createDine() {
        User auth;
        String error = "";

        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            if( auth.getRole() == User.Role.ROLE_MANAGER ) {
                dineService.createDineInRestaurant(auth.getRestaurant());
                return new ResponseStatus(true, "dine created");
            }
        } catch (Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus( false, error);
    }

    @DeleteMapping("/dine/delete/{id}")
    public ResponseStatus deleteDine(@PathVariable String id) {
        User auth;
        String error = "";

        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            if ( auth.getRole() == User.Role.ROLE_MANAGER ) {
                dineService.deleteDineInRestaurant( Integer.parseInt(id), auth.getRestaurant());
                return new ResponseStatus(true, "dine deleted successfully");
            }
        } catch (Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus(false, error);
    }

    @GetMapping("/dines")
    public ResponseStatus dines() {
        User auth;
        String error = "";
        try {
            auth = authenticatedUser.getAuthenticatedUserObject();
            if( auth.getRole() == User.Role.ROLE_MANAGER ) {
                HashMap<String, Object> map = new HashMap<>(3);
                map.put("total", dineService.getDineCountOfRestaurant(auth.getRestaurant()));
                map.put("available", dineService.getActiveDineCountOfRestaurant(auth.getRestaurant()));
                map.put("dines", dineService.getAllDinesOfRestaurant(auth.getRestaurant()));
                return new ResponseGenericVarArgs<>(true, "success", map);
            }
        } catch (Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus(false, error);
    }

    @GetMapping("/dines/unique-code")
    public ResponseStatus getRestaurantUniqueCode() {
        User auth;
        String error = "";
        try{
            auth = authenticatedUser.getAuthenticatedUserObject();
            if( auth.getRole() == User.Role.ROLE_MANAGER ) {
                Integer code = restaurantService.generateRandomIdRestaurant(auth.getRestaurant());
                return new ResponseGenericObject<>(code, true, "generated code");
            }
        } catch (Exception e) {
            error = String.valueOf(e);
        }
        return new ResponseStatus( false, error);
    }
}
