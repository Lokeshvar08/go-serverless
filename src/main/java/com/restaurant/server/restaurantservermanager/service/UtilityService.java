package com.restaurant.server.restaurantservermanager.service;

import com.lowagie.text.pdf.PdfPTable;
import com.restaurant.server.restaurantservermanager.model.Customer;
import com.restaurant.server.restaurantservermanager.model.Dine;
import com.restaurant.server.restaurantservermanager.model.Transaction;
import com.restaurant.server.restaurantservermanager.model.TransactionItem;
import com.restaurant.server.restaurantservermanager.service.dine.DineService;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;
import com.restaurant.server.restaurantservermanager.service.forms.kitchen.Order;
import com.restaurant.server.restaurantservermanager.service.transaction.TransactionItemService;
import com.restaurant.server.restaurantservermanager.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UtilityService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DineService dineService;

    @Autowired
    private CreatePDF createPDF;

    @Autowired
    private MailSenderService mailSenderService;

    public CreatePDF getCreatePDF() {
        return createPDF;
    }

    public void setCreatePDF(CreatePDF createPDF) {
        this.createPDF = createPDF;
    }

    @Autowired
    private TransactionItemService transactionItemService;

    public TransactionItemService getTransactionItemService() {
        return transactionItemService;
    }

    public void setTransactionItemService(TransactionItemService transactionItemService) {
        this.transactionItemService = transactionItemService;
    }

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
                    List<Order> orders = computeTransactionItems(transaction);
                    OutputStream out = createPDF.export(new ByteArrayOutputStream(),
                            orders, transaction.getRestaurant().getName());
                    mailSenderService.sendEmailWithAttachment(
                            customer.getEmail(),
                            "Your Transaction in "+ dine.getRestaurant().getName(),
                            dine.getRestaurant().getName(),
                            out
                    );
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

    public List<Order> computeTransactionItems(Transaction transaction) {
        List<TransactionItem> transactionItemList = transactionItemService.getOrderedFoods(
                transaction
        );
        List<Order> orders = new ArrayList<>();
        Integer transactionId = transaction.getId();
        double total = 0.0;
        for(TransactionItem food: transactionItemList){
            if( food.getStatus() == TransactionItem.Status.HAPPY_MEAL ) {
                orders.add(
                        new Order(
                                transactionId,
                                food.getId(),
                                food.getFood().getName(),
                                food.getQuantity(),
                                food.getQuantity() * food.getFood().getPrice()
                        )
                );
                total += food.getQuantity() * food.getFood().getPrice();
            }
        }
        transaction.setTotal(total);
        transactionService.saveTransaction(transaction);
        return orders;
    }
}
