package com.restaurant.server.restaurantservermanager.service;

import com.restaurant.server.restaurantservermanager.controller.response.ResponseStatus;
import com.restaurant.server.restaurantservermanager.model.Customer;
import com.restaurant.server.restaurantservermanager.model.Dine;
import com.restaurant.server.restaurantservermanager.model.Transaction;
import com.restaurant.server.restaurantservermanager.service.dine.DineService;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;
import com.restaurant.server.restaurantservermanager.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class InvalidateCustomer {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DineService dineService;

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public DineService getDineService() {
        return dineService;
    }

    public void setDineService(DineService dineService) {
        this.dineService = dineService;
    }

    public void invalidateCustomer(HttpServletRequest request) throws ServiceErrorHandler {
        Dine dine = null;
        Customer customer = null;
        Transaction transaction = null;
        try {
            if( request.isRequestedSessionIdValid() ) {
                dine = (Dine) request.getAttribute("dine");
                customer = (Customer) request.getSession().getAttribute("customer");
                transaction = (Transaction) request.getSession().getAttribute("transaction");
                if( dine != null && customer != null) {
                    transaction.setStatus(false);
                    dine.setStatus(true);
                    dine.setOtp(null);
                    transactionService.saveTransaction(transaction);
                    dineService.updateDine(dine);
                    request.getSession().removeAttribute("transaction");
                    request.getSession().removeAttribute("customer");
                } else {
                    request.getSession().invalidate();
                    throw new ServiceErrorHandler("session not valid: dine or customer is null");
                }

            } else {
                throw new ServiceErrorHandler("session not valid: no existing session");
            }
        } catch ( Exception e) {
            throw new ServiceErrorHandler("error in invalidation");
        }
    }
}
