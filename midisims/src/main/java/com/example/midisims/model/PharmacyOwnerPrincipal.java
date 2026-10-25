package com.example.midisims.model;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class PharmacyOwnerPrincipal implements UserDetails {
    private PharmacyOwner owner;
    public PharmacyOwnerPrincipal(PharmacyOwner owner) {
        this.owner=owner;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return owner.getPassword();
    }

    @Override
    public String getUsername() {
        return owner.getEmail();
    }
}
