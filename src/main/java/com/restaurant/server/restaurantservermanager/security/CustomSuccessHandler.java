package com.restaurant.server.restaurantservermanager.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectUrl = null;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if ( grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                redirectUrl = "/admin/add-employee";
                break;
            } else if ( grantedAuthority.getAuthority().equals("ROLE_MANAGER")) {
                redirectUrl = "/manager/create-employee";
                break;
            } else if( grantedAuthority.getAuthority().equals("ROLE_KITCHEN")) {
                redirectUrl = "/kitchen/manage-food";
                break;
            } else {
                redirectUrl = "/cashier/home";
                break;
            }
        }
        System.out.println("redirectUrl " + redirectUrl);
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
