package com.restaurant.server.restaurantservermanager.service.forms.food;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class FoodCreate {

    private String name;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
