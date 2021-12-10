package com.restaurant.server.restaurantservermanager.service.errors;

public class ServiceErrorHandler extends Exception {
    private final String message;

    public ServiceErrorHandler(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CreationErrorHandler{" +
                "message='" + message + '\'' +
                '}';
    }
}
