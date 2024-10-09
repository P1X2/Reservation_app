package com.example.Reservation_app.Authentication;

import com.example.Reservation_app.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        com.example.Reservation_app.Users.User.User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user in DB"));

        if(user.getUserStatus().name().equals("SUSPENDED"))
        {
            throw new UsernameNotFoundException("User is suspended");
        }

        SimpleGrantedAuthority SGA = new SimpleGrantedAuthority("ROLE_"+user.getRole().name());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
