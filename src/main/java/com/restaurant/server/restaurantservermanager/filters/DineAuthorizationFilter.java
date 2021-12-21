package com.restaurant.server.restaurantservermanager.filters;

import com.restaurant.server.restaurantservermanager.model.Dine;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class DineAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !(
                request.getServletPath().startsWith("/api/customer/")
                || request.getServletPath().startsWith("/customer/")
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if( session.getAttribute("dine") != null ){
            try {
                Dine dine = (Dine) session.getAttribute("dine");
                request.setAttribute("dine", dine);
                filterChain.doFilter(request, response);
            } catch (Exception e){
                e.printStackTrace();
                session.invalidate();
            }
        } else {
            session.invalidate();
            request.setAttribute("error", "dine does not exist");
            response.sendRedirect("http://localhost:8080/customer");
        }
    }
}
