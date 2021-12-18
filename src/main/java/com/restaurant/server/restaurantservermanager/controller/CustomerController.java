package com.restaurant.server.restaurantservermanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/register")
    public String getRegister() {
        return "customer/register";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "customer/login";
    }

    @GetMapping("/menu")
    public String getMenu() {
        return "customer/menu";
    }

    @GetMapping("/bill")
    public String getCart() {
        return "customer/bill";
    }

    @GetMapping("/manage1")
    public String getTemp1() {
        return "manager/manage";
    }

    @GetMapping("/manage2")
    public String getTemp2() {
        return "admin/manage";
    }
}

