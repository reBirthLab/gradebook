package com.rebirthlab.gradebook.auth.model;

import com.rebirthlab.gradebook.auth.service.UserDTO;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Anastasiy
 */
@Entity
public class User implements UserDetails {

    @Id
    @NotNull
    private String email;

    @NotNull
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public User() {
        roles = new HashSet<>();
    }

    public User(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.roles = new HashSet<>(userDTO.getRoles());
        this.isAccountNonExpired = userDTO.isAccountNonExpired();
        this.isAccountNonLocked = userDTO.isAccountNonLocked();
        this.isCredentialsNonExpired = userDTO.isCredentialsNonExpired();
        this.isEnabled = userDTO.isEnabled();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (roles != null) {
            for (String role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
