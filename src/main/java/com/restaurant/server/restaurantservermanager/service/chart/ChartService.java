package com.restaurant.server.restaurantservermanager.service.chart;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ChartService {
    List<List<Map<Object, Object>>> getFoodChart(Date from, Date to, Restaurant restaurant);
}
