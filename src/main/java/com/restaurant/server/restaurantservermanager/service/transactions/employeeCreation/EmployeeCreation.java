package com.restaurant.server.restaurantservermanager.service.transactions.employeeCreation;

import com.restaurant.server.restaurantservermanager.model.Restaurant;

public interface EmployeeCreation {
    void createEmployee(
            String username,
            String password,
            String name,
            String phone,
            String role,
            Restaurant restaurant
    ) throws Exception;
}
