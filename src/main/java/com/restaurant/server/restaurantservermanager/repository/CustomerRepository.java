package com.restaurant.server.restaurantservermanager.repository;

import com.restaurant.server.restaurantservermanager.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}