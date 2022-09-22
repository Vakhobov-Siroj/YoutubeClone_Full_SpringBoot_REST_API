package com.company.config;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class CustomUserDetails implements UserDetails {
    private ProfileEntity profile;

    public CustomUserDetails(ProfileEntity profile) {
        this.profile = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
  /*      List<SimpleGrantedAuthority> roles = new LinkedList<>();
        roles.add(new SimpleGrantedAuthority(profile.getRole().name()));
        return roles;*/
        return new LinkedList<>(Collections.singletonList(new SimpleGrantedAuthority(profile.getRole().name())));
    }

    @Override
    public String getPassword() {
        return profile.getPassword();
    }

    @Override
    public String getUsername() {
        return profile.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return profile.getVisible();
    }


    public ProfileEntity getProfile() {
        return profile;
    }
}
