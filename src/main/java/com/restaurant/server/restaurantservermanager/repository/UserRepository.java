package com.restaurant.server.restaurantservermanager.repository;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {


    List<User> queryUsersByRestaurantAndRoleIsNot(Restaurant restaurant, User.Role role);
    List<User> queryUsersByRestaurantAndRoleIsNotAndRoleIsNot(
            Restaurant restaurant,
            User.Role role1,
            User.Role role2);

    User getUserByIdAndRestaurant(Integer userId, Restaurant restaurant);

    @Query("FROM User u WHERE u.username=?1")
    User getUserByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=?1 AND u.restaurant=?2 AND u.role<>?3")
    int deleteUserByAdmin(Integer id, Restaurant restaurant, User.Role admin);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=?1 AND u.restaurant=?2 AND u.role<>?3 AND u.role<>?4")
    int deleteUserByManager(Integer id, Restaurant restaurant, User.Role admin, User.Role manager);


}