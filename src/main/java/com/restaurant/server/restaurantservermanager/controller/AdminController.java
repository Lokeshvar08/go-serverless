package com.restaurant.server.restaurantservermanager.controller;

import com.restaurant.server.restaurantservermanager.security.AuthenticatedUser;
import com.restaurant.server.restaurantservermanager.service.transactions.employeeCreation.EmployeeCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmployeeCreation employeeCreation;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    public EmployeeCreation getEmployeeCreationImpl() {
        return employeeCreation;
    }

    public void setEmployeeCreationImpl(EmployeeCreation employeeCreationImpl) {
        this.employeeCreation = employeeCreationImpl;
    }

    public AuthenticatedUser getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }



    @GetMapping("/home")
    public String home() {

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println( ((UserDetails)user).getUsername());
        return "admin/admin";
    }

    @GetMapping("/create-employee")
    public String createEmployee(ModelAndView mv) {
        return "admin/add-employee";
    }

    @PostMapping ("/save-employee")
    public ModelAndView saveEmployee(HttpServletRequest request, ModelAndView mv) {
        try{

            String role = "ROLE_" + request.getParameter("role").toUpperCase();
            if( role.equals("ROLE_MANAGER") || role.equals("ROLE_KITCHEN") || role.equals("ROLE_CASHIER")) {
                employeeCreation.createEmployee(
                        request.getParameter("username"),
                        request.getParameter("password"),
                        request.getParameter("name"),
                        request.getParameter("phone"),
                        role,
                        authenticatedUser.getAuthenticatedUserObject().getRestaurant()
                );
                mv.setViewName("admin/add-employee");
                mv.addObject("creation", "Employee Creation Successful!");
            } else{
                throw new Exception("Invalid Role");
            }

        } catch (Exception e) {
            mv.setViewName("admin/add-employee");
            mv.addObject("creation", "Employee Creation UnSuccessful!");
        }
        return mv;
    }
}
