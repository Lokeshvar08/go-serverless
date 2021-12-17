package com.restaurant.server.restaurantservermanager.repository;

import com.restaurant.server.restaurantservermanager.model.Dine;
import com.restaurant.server.restaurantservermanager.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DineRepository extends JpaRepository<Dine, Integer> {
    Integer countDinesByRestaurant(Restaurant restaurant);

    Integer countDinesByRestaurantAndStatus(Restaurant restaurant, Boolean status);

    List<Dine> getDinesByRestaurant(Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dine d WHERE d.id=?1 AND d.restaurant=?2")
    Integer deleteByIdAndRestaurant( Integer id, Restaurant restaurant);

    @Query("FROM Dine d WHERE d.restaurant=?1 AND d.number=?2 AND d.status=?3")
    Dine getDineByRestaurant( Restaurant restaurant, Integer number, Boolean status);

}