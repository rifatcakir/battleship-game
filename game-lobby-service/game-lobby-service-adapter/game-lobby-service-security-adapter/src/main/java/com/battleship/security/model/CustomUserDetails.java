package com.battleship.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private final UserInfo userInfo;
    private final List<SimpleGrantedAuthority> authorities;

    public CustomUserDetails(UserInfo user, List<SimpleGrantedAuthority> authorities) {
        this.userInfo = user;
        this.authorities = authorities;
    }

    public UUID getId() {
        return userInfo.getId();
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
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

}
