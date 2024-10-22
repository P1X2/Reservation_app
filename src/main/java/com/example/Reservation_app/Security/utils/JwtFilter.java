package com.example.Reservation_app.Security.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ApplicationContext context;

    // Logger instance
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Log the incoming request URL
        logger.info("Incoming request to URL: {}", request.getRequestURI());

        // Validate token
        if (authHeader == null) {
            logger.warn("Missing Authorization header");
        } else if (!authHeader.startsWith("Bearer ")) {
            logger.warn("Invalid Authorization header format: {}", authHeader);
        } else {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
            logger.info("Extracted token and username: {}, {}", token, username);
        }

        // Authentication object check
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = context.getBean(UserDetailsService.class).loadUserByUsername(username);
            logger.info("Loaded UserDetails for username: {}", username);

            if (jwtService.validateToken(token, userDetails)) {
                logger.info("Token is valid for user: {}", username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                logger.warn("Token validation failed for user: {}", username);
            }
        } else {
            logger.warn("Username is null or authentication already exists.");
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
