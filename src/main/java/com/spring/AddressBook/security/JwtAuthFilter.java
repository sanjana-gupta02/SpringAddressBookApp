package com.spring.AddressBook.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // ✅ Check if header is missing or does not start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // ✅ Extract JWT token from header
        String token = authHeader.substring(7);
        String email = jwtUtil.extractClaims(token).getSubject();

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // ✅ Create authentication object
            User user = new User(email, "", new java.util.ArrayList<>());
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(user, token);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // ✅ Set authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
