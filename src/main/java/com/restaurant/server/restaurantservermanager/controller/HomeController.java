package com.restaurant.server.restaurantservermanager.controller;

import com.restaurant.server.restaurantservermanager.model.Dine;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.service.dine.DineService;
import com.restaurant.server.restaurantservermanager.service.restaurant.RestaurantService;
import com.restaurant.server.restaurantservermanager.service.user.UserService;
import com.restaurant.server.restaurantservermanager.service.transactions.restaurantCreation.RestaurantCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {


    @Autowired
    private RestaurantCreation restaurantCreation;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private DineService dineService;

    public RestaurantCreation getRestaurantCreation() {
        return restaurantCreation;
    }

    public void setRestaurantCreation(RestaurantCreation restaurantCreation) {
        this.restaurantCreation = restaurantCreation;
    }

    public RestaurantService getRestaurantService() {
        return restaurantService;
    }

    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

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

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String loginPage() {
        return "auth/login";
    }

    @RequestMapping(value = {"/login-error"}, method = RequestMethod.GET)
    public ModelAndView loginPageError(ModelAndView mv) {
        mv.setViewName("auth/login");
        mv.addObject("loginError", true);
        return mv;
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String registerForm() {
        System.out.println("inside register");
        return "auth/register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public ModelAndView createRestaurant(HttpServletRequest request, ModelAndView mv) {
        try {
            restaurantCreation.createRestaurant(
                    request.getParameter("username"),
                    request.getParameter("password"),
                    request.getParameter("name"),
                    request.getParameter("phone"),
                    request.getParameter("restaurant_name"),
                    request.getParameter("restaurant_branch"),
                    request.getParameter("restaurant_city")
            );
            mv.setStatus(HttpStatus.CREATED);
            mv.addObject("creation","Restaurant Creation Successful! Login Here...");
            mv.setViewName("auth/login");
        } catch (Exception e) {
            mv.setStatus(HttpStatus.BAD_REQUEST);
            mv.addObject("creation","failed");
            mv.setViewName("auth/register");
        }
        return mv;
    }

    @GetMapping("/customer")
    public ModelAndView getCustomer(ModelAndView mv) {
        mv.setViewName("customer/home");
        return mv;
    }

    @PostMapping("/customer")
    public ModelAndView loginCustomer(HttpServletRequest request, ModelAndView mv) {
        String username = request.getParameter("username");
        String unicode = request.getParameter("unicode");
        String number = request.getParameter("number");
        User user = userService.findUserByUsername(username);
        Integer random_code = user.getRestaurant().getRandom_code();
        if( random_code != null && random_code == Integer.parseInt(unicode)) {
            Dine dine = dineService.getDineByNumberAndRestaurant(
                    Integer.parseInt(number), user.getRestaurant());
            if(dine != null ){
                HttpSession session = request.getSession();
                session.setAttribute("dine", dine);
                mv.setViewName("customer/customer-launch");
                mv.addObject("dine", dine);
                return mv;
            }
        }

        mv.setViewName("customer/home");
        mv.addObject("error", "not able to get the specified details");
        return mv;
    }


}
