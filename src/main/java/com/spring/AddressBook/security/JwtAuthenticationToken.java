package com.spring.AddressBook.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.User;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final User principal;
    private final String token;

    public JwtAuthenticationToken(User principal, String token) {
        super(principal.getAuthorities());
        this.principal = principal;
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
