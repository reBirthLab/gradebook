package com.rebirthlab.gradebook.application.service.user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anastasiy
 */
public class UserDetails {

    private final String clientId;
    private final String username;
    private final List<String> scopes;
    private final Instant expirationDate;
    private final List<String> authorities;

    public UserDetails(String clientId, String username, List<String> scopes,
                       Instant expirationDate, List<String> authorities) {
        this.clientId = clientId;
        this.username = username;
        this.scopes = new ArrayList<>(scopes);
        this.expirationDate = expirationDate;
        this.authorities = new ArrayList<>(authorities);
    }

    public String getClientId() {
        return clientId;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
