package com.restaurant.server.restaurantservermanager.service.chart;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.Transaction;
import com.restaurant.server.restaurantservermanager.model.TransactionItem;
import com.restaurant.server.restaurantservermanager.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChartServiceImpl implements ChartService{

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<List<Map<Object, Object>>> getFoodChart(Date from, Date to, Restaurant restaurant) {
        List<Transaction>
        transactions = transactionRepository.getTransactionsByUpdateDateAfterAndUpdateDateBeforeAndRestaurant(
                from,
                to,
                restaurant);

        Map<String,Integer> map = new HashMap<>();
        List<List<Map<Object,Object>>> list = new ArrayList<>();
        List<Map<Object,Object>> dataPoints1 = new ArrayList<>();

        for( Transaction transaction: transactions) {
            for(TransactionItem transactionItem: transaction.getFoodItems()) {
                map.merge(transactionItem.getFood().getName(), 1, Integer::sum);
            }
        }

        for( Map.Entry<String, Integer> entry: map.entrySet()) {
            HashMap<Object, Object> temp = new HashMap<>();
            temp.put("label", entry.getKey());
            temp.put("y", entry.getValue());
            dataPoints1.add(temp);
        }
        list.add(dataPoints1);
        return list;
    }
}
