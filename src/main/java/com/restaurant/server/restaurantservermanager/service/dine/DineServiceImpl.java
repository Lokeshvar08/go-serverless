package com.restaurant.server.restaurantservermanager.service.dine;

import com.restaurant.server.restaurantservermanager.model.Dine;
import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.repository.DineRepository;
import com.restaurant.server.restaurantservermanager.service.dine.DineService;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DineServiceImpl implements DineService {

    @Autowired
    private DineRepository dineRepository;

    public DineRepository getDineRepository() {
        return dineRepository;
    }

    public void setDineRepository(DineRepository dineRepository) {
        this.dineRepository = dineRepository;
    }

    @Override
    public void createDineInRestaurant(Restaurant restaurant) {
        int count = getDineCountOfRestaurant(restaurant);
        System.out.println("current table count: " + count);
        Dine dine = new Dine();
        dine.setRestaurant(restaurant);
        dine.setNumber(count+1);
        dine.setStatus(true);
        dineRepository.save(dine);
        System.out.println("updated table count: " + dine.getNumber());
    }

    @Override
    public List<Dine> getAllDinesOfRestaurant(Restaurant restaurant) {
        return dineRepository.getDinesByRestaurant(restaurant);
    }

    @Override
    public void deleteDineInRestaurant(Integer id, Restaurant restaurant) throws ServiceErrorHandler {
        int n = dineRepository.deleteByIdAndRestaurant( id, restaurant);
        if( n != 1 ) {
            throw new ServiceErrorHandler("dine not deleted or exist");
        }
    }

    @Override
    public Integer getDineCountOfRestaurant(Restaurant restaurant) {
        return dineRepository.countDinesByRestaurant(restaurant);
    }

    @Override
    public Integer getActiveDineCountOfRestaurant( Restaurant restaurant) {
        return dineRepository.countDinesByRestaurantAndStatus( restaurant, true);
    }

    @Override
    public Dine getAvailableDineByNumberAndRestaurant(Integer number, Restaurant restaurant) {
        return dineRepository.getDineByRestaurant(restaurant, number, true);
    }

    @Override
    public void updateDine( Dine dine) {
        dineRepository.save(dine);
    }
}
