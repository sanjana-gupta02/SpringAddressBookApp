package com.spring.AddressBook.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;
    private final String token;

    // Constructor to initialize with the user details, token, and authorities
    public JwtAuthenticationToken(UserDetails principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        setAuthenticated(true);  // We set this to true because the token has been validated
    }

    @Override
    public Object getCredentials() {
        return token;  // Return the token as the credentials
    }

    @Override
    public Object getPrincipal() {
        return principal;  // Return the user details as the principal
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        // The token has already been validated, so no need to change the authenticated state.
        if (authenticated) {
            super.setAuthenticated(true);  // Always ensure this is true if validated
        }
    }
}
