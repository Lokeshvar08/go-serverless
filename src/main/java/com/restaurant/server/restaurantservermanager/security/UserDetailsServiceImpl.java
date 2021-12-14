package com.restaurant.server.restaurantservermanager.security;

import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if( user == null ) {
            throw new UsernameNotFoundException("could not find user: " + username);
        }
        return new MyUserDetails(user);
    }
}
