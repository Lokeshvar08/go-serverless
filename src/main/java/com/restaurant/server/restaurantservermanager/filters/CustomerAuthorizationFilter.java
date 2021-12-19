package com.restaurant.server.restaurantservermanager.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomerAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !(
                request.getServletPath().startsWith("/customer/menu")
                || request.getServletPath().startsWith("/customer/bill")
                || request.getServletPath().startsWith("/customer/pay")
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if( session.getAttribute("customer") != null ) {
               filterChain.doFilter(request,response);
        } else {
               response.sendRedirect("http://localhost:8080/customer/login");
        }
    }
}
