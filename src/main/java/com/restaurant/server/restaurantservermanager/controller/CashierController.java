package com.restaurant.server.restaurantservermanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cashier")
public class CashierController {

    @GetMapping("/home")
    public String home() {
        return "cashier/cashier";
    }
}
