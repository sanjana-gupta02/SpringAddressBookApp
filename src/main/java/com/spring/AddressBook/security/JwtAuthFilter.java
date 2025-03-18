package com.spring.AddressBook.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthFilter.class.getName());

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, @Lazy UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);  // If no valid token, proceed with the request
            return;
        }

        String token = authHeader.substring(7);  // Extract the token from the header
        String email = null;

        try {
            email = jwtUtil.extractEmail(token);  // Extract email from the token
        } catch (Exception e) {
            LOGGER.warning("Invalid or expired JWT token: " + e.getMessage());
            chain.doFilter(request, response);  // Proceed without authentication if token is invalid
            return;
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details using the email extracted from token
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // If the token is valid, set the authentication in SecurityContextHolder
            if (jwtUtil.validateToken(token, userDetails)) {
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);  // Continue with the filter chain
    }
}
