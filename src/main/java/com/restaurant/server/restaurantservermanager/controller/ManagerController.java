package com.restaurant.server.restaurantservermanager.controller;

import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.security.AuthenticatedUser;
import com.restaurant.server.restaurantservermanager.service.forms.user.AuthenticatedUserClient;
import com.restaurant.server.restaurantservermanager.service.transactions.employeeCreation.EmployeeCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private EmployeeCreation employeeCreation;

    public AuthenticatedUser getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public EmployeeCreation getEmployeeCreation() {
        return employeeCreation;
    }

    public void setEmployeeCreation(EmployeeCreation employeeCreation) {
        this.employeeCreation = employeeCreation;
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView mv) {
        try {
            mv.setViewName("manager/manage");
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

    @GetMapping("/create-employee")
    public ModelAndView createEmployee(ModelAndView mv) {
        try {
            mv.setViewName("manager/add-cooks");
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

    @PostMapping("/save-employee")
    public ModelAndView saveEmployee(HttpServletRequest request, ModelAndView mv) {
        try{

            String role = "ROLE_" + request.getParameter("role").toUpperCase();
            if(role.equals("ROLE_KITCHEN") || role.equals("ROLE_CASHIER")) {
                employeeCreation.createEmployee(
                        request.getParameter("username"),
                        request.getParameter("password"),
                        request.getParameter("name"),
                        request.getParameter("phone"),
                        role,
                        authenticatedUser.getAuthenticatedUserObject().getRestaurant()
                );
                User user = authenticatedUser.getAuthenticatedUserObject();
                mv.addObject("user", new AuthenticatedUserClient(
                        user.getId(),
                        user.getUsername(),
                        user.getName(),
                        user.getRole().toString(),
                        user.getRestaurant().getName(),
                        user.getRestaurant().getId()
                ));
                mv.setViewName("manager/add-cooks");
                mv.addObject("creation", "Employee Creation Successful!");
            } else{
                throw new Exception("Invalid Role");
            }

        } catch (Exception e) {
            mv.setViewName("manager/add-cooks");
            mv.addObject("creation", "Employee Creation UnSuccessful!");
        }
        return mv;
    }
}
