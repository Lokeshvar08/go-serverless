package com.restaurant.server.restaurantservermanager.service.errors;

public class CreationErrorHandler extends Exception {
    private final String message;

    public CreationErrorHandler(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CreationErrorHandler{" +
                "message='" + message + '\'' +
                '}';
    }
}
