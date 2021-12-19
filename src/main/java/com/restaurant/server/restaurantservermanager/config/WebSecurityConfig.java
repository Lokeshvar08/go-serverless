package com.restaurant.server.restaurantservermanager.config;

import com.restaurant.server.restaurantservermanager.security.CustomSuccessHandler;
import com.restaurant.server.restaurantservermanager.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomSuccessHandler customSuccessHandler;

    @Autowired
    public WebSecurityConfig(
            PasswordEncoder passwordEncoder,
            UserDetailsServiceImpl userDetailsService,
            CustomSuccessHandler customSuccessHandler) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.customSuccessHandler = customSuccessHandler;
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Inside the Security Configure configure(HttpSecurity http)");
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/api/manager/**").hasRole("MANAGER")
                .antMatchers("/manager/**").hasRole("MANAGER")
                .antMatchers("/api/kitchen/**").hasRole("KITCHEN")
                .antMatchers("/kitchen/**").hasRole("KITCHEN")
                .antMatchers("/api/cashier/**").hasRole("CASHIER")
                .antMatchers("/cashier/**").hasRole("CASHIER")
                .antMatchers(
                        "/",
                        "/register",
                        "/customer/**",
                        "/api/customer/**", "/socket").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(customSuccessHandler)
                .failureUrl("/login-error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");

    }
}


