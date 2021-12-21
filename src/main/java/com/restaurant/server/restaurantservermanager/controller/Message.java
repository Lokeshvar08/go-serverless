package com.restaurant.server.restaurantservermanager.controller;

public class Message {
    private Integer dine;
    private Integer Restaurant;
    private String message;

    public Integer getDine() {
        return dine;
    }

    public void setDine(Integer dine) {
        this.dine = dine;
    }

    public Integer getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        Restaurant = restaurant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "Message{" +
                "dine=" + dine +
                ", Restaurant=" + Restaurant +
                ", message='" + message + '\'' +
                '}';
    }
}
