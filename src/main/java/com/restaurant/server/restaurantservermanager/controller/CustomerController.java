package com.restaurant.server.restaurantservermanager.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.restaurant.server.restaurantservermanager.controller.response.ResponseStatus;
import com.restaurant.server.restaurantservermanager.model.Customer;
import com.restaurant.server.restaurantservermanager.model.Dine;
import com.restaurant.server.restaurantservermanager.model.Transaction;
import com.restaurant.server.restaurantservermanager.service.InvalidateCustomer;
import com.restaurant.server.restaurantservermanager.service.PaypalService;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;
import com.restaurant.server.restaurantservermanager.service.forms.customer.DineDataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private InvalidateCustomer invalidateCustomer;

    public InvalidateCustomer getInvalidateCustomer() {
        return invalidateCustomer;
    }

    public void setInvalidateCustomer(InvalidateCustomer invalidateCustomer) {
        this.invalidateCustomer = invalidateCustomer;
    }


    @GetMapping("/login")
    public ModelAndView getLogin(HttpServletRequest request, ModelAndView mv) {
        Dine dine = (Dine) request.getSession().getAttribute("dine");
        mv.setViewName("customer/login");
        mv.addObject("dine",new DineDataClient(
                dine.getId(),
                dine.getNumber(),
                dine.getRestaurant().getId(),
                dine.getRestaurant().getName()
        ));
        return mv;
    }

    @GetMapping("/menu")
    public ModelAndView getMenu(HttpServletRequest request, ModelAndView mv) {
        Dine dine = (Dine) request.getSession().getAttribute("dine");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        mv.setViewName("customer/menu");
        mv.addObject("dine", new DineDataClient(
                dine.getId(),
                dine.getNumber(),
                dine.getRestaurant().getId(),
                dine.getRestaurant().getName(),
                customer.getEmail()
        ));
        return mv;
    }

    @GetMapping("/bill")
    public ModelAndView getCart(HttpServletRequest request, ModelAndView mv) {
        Dine dine = (Dine) request.getSession().getAttribute("dine");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Transaction transaction = (Transaction) request.getSession().getAttribute("transaction");
        mv.setViewName("customer/bill");
        DineDataClient ddc = new DineDataClient(
                dine.getId(),
                dine.getNumber(),
                dine.getRestaurant().getId(),
                dine.getRestaurant().getName(),
                customer.getEmail()
        );
        if( transaction != null) {
            ddc.setTransactionId(transaction.getId());
        }
        mv.addObject("dine",ddc);
        return mv;
    }

    public PaypalService getPaypalService() {
        return paypalService;
    }

    public void setPaypalService(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @PostMapping("/pay")
    public String payment(HttpServletRequest request, HttpServletResponse response){
        Dine dine = null;
        Customer customer = null;
        Transaction transaction = null;
        try {
            System.out.println("just entered payment()");
            if( request.isRequestedSessionIdValid() ) {
                dine = (Dine) request.getAttribute("dine");
                customer = (Customer) request.getSession().getAttribute("customer");
                transaction = (Transaction) request.getSession().getAttribute("transaction");
                System.out.println("validating dine|customer|transaction");
                if( dine != null && customer != null) {
                    System.out.println("validated dine|customer|transaction");
                    Payment payment = paypalService.createPayment(
                            transaction.getTotal(),
                            "USD",
                            "paypal",
                            "sale",
                            "food purchase",
                            "http://localhost:8080/customer/pay/failed",
                            "http://localhost:8080/customer/pay/success"
                    );
                    System.out.println("payment created");
                    System.out.println(payment.getLinks());
                    for(Links link: payment.getLinks()) {
                        System.out.println(link.getRel()+" "+link.getHref());
                        if(link.getRel().equals("approval_url")) {
                            return "redirect:"+link.getHref();
                        }
                    }
                    System.out.println("links for loop");

                } else {
                    request.getSession().invalidate();
                    throw new ServiceErrorHandler("session not valid: dine or customer is null");
                }

            } else {
                throw new ServiceErrorHandler("session not valid: no existing session");
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return "redirect:/customer/bill";
    }

    @GetMapping("/pay/failed")
    public ModelAndView failedPayment(ModelAndView mv) {
        mv.setViewName("customer/bill");
        mv.addObject("error",true);
        return mv;
    }
    @GetMapping("/pay/success")
    public ModelAndView successPay(@RequestParam("paymentId") String paymentId,
                                   @RequestParam("PayerID") String payerId,
                                   HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/bill");
        try {
            System.out.println("successPay()");
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println("payment executed");
            System.out.println(payment.toJSON());
            invalidateCustomer.invalidateCustomer(request);
            if (payment.getState().equals("approved")) {
                mv.addObject("payment", true);
                return mv;
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
            mv.addObject("error",e);
        } catch (Exception e) {
            mv.addObject("error", e);
        }
        return mv;
    }
}

