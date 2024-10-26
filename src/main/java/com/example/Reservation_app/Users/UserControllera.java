package com.example.Reservation_app.Users;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserControllera {

    @GetMapping("/current-user")
    public String getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // If UserDetails is being used
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                return "Username: " + username + ", Roles: " + authorities;
            } else {
                // If it's just a simple string (username)
                return "Principal: " + principal.toString();
            }
        }
        return "No authenticated user found";
    }
}
