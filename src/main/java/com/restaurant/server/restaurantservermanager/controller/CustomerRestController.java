package com.restaurant.server.restaurantservermanager.controller;

import com.restaurant.server.restaurantservermanager.controller.response.ResponseGenericListObject;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseGenericObject;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseStatus;

import com.restaurant.server.restaurantservermanager.model.*;
import com.restaurant.server.restaurantservermanager.service.*;
import com.restaurant.server.restaurantservermanager.service.customer.CustomerService;
import com.restaurant.server.restaurantservermanager.service.dine.DineService;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;
import com.restaurant.server.restaurantservermanager.service.food.FoodService;
import com.restaurant.server.restaurantservermanager.service.forms.customer.OtpValidation;
import com.restaurant.server.restaurantservermanager.service.forms.customer.SendMail;
import com.restaurant.server.restaurantservermanager.service.forms.food.OrderFood;
import com.restaurant.server.restaurantservermanager.service.forms.kitchen.Order;
import com.restaurant.server.restaurantservermanager.service.transaction.TransactionItemService;
import com.restaurant.server.restaurantservermanager.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private DineService dineService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionItemService transactionItemService;

    public MailSenderService getMailSenderService() {
        return mailSenderService;
    }

    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    public DineService getDineService() {
        return dineService;
    }

    public void setDineService(DineService dineService) {
        this.dineService = dineService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public FoodService getFoodService() {
        return foodService;
    }

    public void setFoodService(FoodService foodService) {
        this.foodService = foodService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionItemService getTransactionItemService() {
        return transactionItemService;
    }

    public void setTransactionItemService(TransactionItemService transactionItemService) {
        this.transactionItemService = transactionItemService;
    }

    @PostMapping("/invalidate-dine")
    public ResponseStatus invalidateDine(HttpServletRequest request) {
        Dine dine = null;
        try{
            dine = (Dine) request.getAttribute("dine");
            if( dine != null) {
                request.getSession().invalidate();
                return new ResponseStatus(true, "invalidated");
            } else {
                throw new ServiceErrorHandler("already invalidated session");
            }
        } catch ( Exception e) {
            return new ResponseStatus(false, String.valueOf(e));
        }
    }

    @PostMapping("/send-otp")
    public ResponseStatus sendOtpMail(HttpServletRequest request, @RequestBody SendMail sendMail) {
        Dine dine = null;
        try {
            if(request.isRequestedSessionIdValid()) {
                int random = ThreadLocalRandom.current().nextInt(10000, 100000);
                dine = (Dine) request.getAttribute("dine");
                if( dine != null){
                    dine.setOtp(random);
                    dineService.updateDine(dine);
                    mailSenderService.sendSimpleEmail(
                            sendMail.getEmail(),
                            "OTP: " + random,
                            dine.getRestaurant().getName() + ": Dine OTP");
                    return new ResponseStatus( true, "otp sent: "+random);
                } else {
                    request.getSession().invalidate();
                    throw new ServiceErrorHandler("session not valid: dine is null");
                }

            } else{
                throw new ServiceErrorHandler("session not valid: no existing session");
            }
        } catch ( Exception e) {
            if( dine != null ) {
                dine.setOtp(null);
                dineService.updateDine(dine);
            }
            return new ResponseStatus(false, String.valueOf(e));
        }
    }

    @PostMapping("/validate-customer")
    public ResponseStatus validateCustomer(
            HttpServletRequest request, @RequestBody OtpValidation otp_validation) {
        Dine dine = null;
        try {
            if( request.isRequestedSessionIdValid()) {
                dine = (Dine) request.getAttribute("dine");
                if( dine != null ) {
                    if( dine.getOtp() == Integer.parseInt(otp_validation.getOtp()) ){
                        Customer customer = new Customer();
                        customer.setEmail(otp_validation.getEmail());
                        customerService.createCustomer(customer);
                        dine.setStatus(false);
                        dineService.updateDine(dine);
                        HttpSession session = request.getSession();
                        session.setAttribute("dine", dine);
                        session.setAttribute("customer", customer);
                        return new ResponseGenericObject<>(customer, true, "success");
                    } else{
                        return new ResponseStatus(false, "wrong otp!");
                    }
                } else {
                    request.getSession().invalidate();
                    throw new ServiceErrorHandler("session not valid: dine is null");
                }
            } else {
                throw new ServiceErrorHandler("session not valid: no existing session");
            }
        } catch ( Exception e) {
            return new ResponseStatus( false, String.valueOf(e));
        }
    }

    @PostMapping("/invalidate-customer")
    public ResponseStatus invalidateCustomer( HttpServletRequest request) {
        Dine dine = null;
        Customer customer = null;
        try {
            if( request.isRequestedSessionIdValid() ) {
                dine = (Dine) request.getAttribute("dine");
                customer = (Customer) request.getSession().getAttribute("customer");
                if( dine != null && customer != null) {
                    HttpSession session = request.getSession();
                    session.removeAttribute("customer");
                    dine.setStatus(true);
                    dine.setOtp(null);
                    dineService.updateDine(dine);
                    dineService.updateDine(dine);
                    return new ResponseStatus(true, "check out customer success");
                } else {
                    request.getSession().invalidate();
                    throw new ServiceErrorHandler("session not valid: dine or customer is null");
                }
            } else {
                throw new ServiceErrorHandler("session not valid: no existing session");
            }
        } catch ( Exception e) {
            return new ResponseStatus( false, String.valueOf(e));
        }
    }

    @GetMapping("/get-food-items")
    public ResponseStatus getFoodItems(HttpServletRequest  request) {
        Dine dine = null;
        Customer customer = null;
        try {
            if( request.isRequestedSessionIdValid() ) {
                dine = (Dine) request.getAttribute("dine");
                customer = (Customer) request.getSession().getAttribute("customer");
                if( dine != null && customer != null) {
                    List<Food> foods = foodService.getAvailableFoodsOfRestaurant(dine.getRestaurant());
                    return new ResponseGenericListObject<>(foods, true, "success");
                } else {
                    request.getSession().invalidate();
                    throw new ServiceErrorHandler("session not valid: dine or customer is null");
                }

            } else {
                throw new ServiceErrorHandler("session not valid: no existing session");
            }
        } catch ( Exception e) {
            return new ResponseStatus( false, String.valueOf(e));
        }
    }

    @PostMapping("/add-food-items")
    public ResponseStatus addFoodItem(HttpServletRequest request, @RequestBody List<OrderFood> foodList) {
        Dine dine = null;
        Customer customer = null;
        try {
            if( request.isRequestedSessionIdValid() ) {
                dine = (Dine) request.getAttribute("dine");
                customer = (Customer) request.getSession().getAttribute("customer");
                if( dine != null && customer != null) {
                    Transaction transaction = transactionService.getOrCreateTransaction(
                            customer,
                            dine
                    );
                    request.getSession().setAttribute("transaction", transaction);
                    List<TransactionItem> transactionItemList = new ArrayList<>();
                    for( OrderFood item: foodList) {
                        TransactionItem order = new TransactionItem();
                        order.setFood(foodService.getFoodByIdAndRestaurant(
                                item.getFoodId(),
                                dine.getRestaurant()
                        ));
                        order.setComment(item.getComment());
                        order.setQuantity(item.getQuantity());
                        order.setStatus(TransactionItem.Status.ORDERED);
                        order.setTransaction(transaction);
                        transactionItemList.add(order);
                    }
                    transactionItemList =  transactionItemService.saveItems(transactionItemList);
                    return new ResponseGenericListObject<>( transactionItemList, true, "success");
                } else {
                    request.getSession().invalidate();
                    throw new ServiceErrorHandler("session not valid: dine or customer is null");
                }

            } else {
                throw new ServiceErrorHandler("session not valid: no existing session");
            }
        } catch ( Exception e) {
            return new ResponseStatus( false, String.valueOf(e));
        }
    }

    @GetMapping("/get-ordered-food-items")
    public ResponseStatus getOrderedFoodItem(HttpServletRequest request) {
        Dine dine = null;
        Customer customer = null;
        Transaction transaction = null;
        try {
            if( request.isRequestedSessionIdValid() ) {
                dine = (Dine) request.getAttribute("dine");
                customer = (Customer) request.getSession().getAttribute("customer");
                transaction = (Transaction) request.getSession().getAttribute("transaction");
                if( dine != null && customer != null) {
                    List<TransactionItem> transactionItemList = transactionItemService.getOrderedFoods(
                            transaction
                    );
                    return new ResponseGenericListObject<>(transactionItemList, true, "success");
                } else {
                    request.getSession().invalidate();
                    throw new ServiceErrorHandler("session not valid: dine or customer is null");
                }

            } else {
                throw new ServiceErrorHandler("session not valid: no existing session");
            }
        } catch ( Exception e) {
            return new ResponseStatus( false, String.valueOf(e));
        }
    }

    @GetMapping("/checkout")
    public ResponseStatus getInvoice(HttpServletRequest request) {
        Dine dine = null;
        Customer customer = null;
        Transaction transaction = null;
        try {
            if( request.isRequestedSessionIdValid() ) {
                dine = (Dine) request.getAttribute("dine");
                customer = (Customer) request.getSession().getAttribute("customer");
                transaction = (Transaction) request.getSession().getAttribute("transaction");
                if( dine != null && customer != null) {
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
                    return new ResponseGenericListObject<>(orders, true, "success");
                } else {
                    request.getSession().invalidate();
                    throw new ServiceErrorHandler("session not valid: dine or customer is null");
                }

            } else {
                throw new ServiceErrorHandler("session not valid: no existing session");
            }
        } catch ( Exception e) {
            return new ResponseStatus( false, String.valueOf(e));
        }
    }


    @PostMapping("/checkout")
    public ResponseStatus checkout(HttpServletRequest request) {
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
                    return new ResponseStatus(true, "success");
                } else {
                    request.getSession().invalidate();
                    throw new ServiceErrorHandler("session not valid: dine or customer is null");
                }

            } else {
                throw new ServiceErrorHandler("session not valid: no existing session");
            }
        } catch ( Exception e) {
            return new ResponseStatus( false, String.valueOf(e));
        }
    }
}
