package com.airxelerate.infrastructure.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.airxelerate.persistence.models.Role;
import com.airxelerate.persistence.models.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserContainer implements UserDetails {

    private String id;

    private String username;

    private String password;

    private List<GrantedAuthority> authorities;

    private User user;

    public UserContainer(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.user = user;
        this.authorities = Optional.ofNullable(user)
                .map(User::getRoles)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .map(Role::getName)
                .filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

}
