package com.example.midisims.service;

import com.example.midisims.model.PharmacyOwner;
import com.example.midisims.model.PharmacyOwnerPrincipal;
import com.example.midisims.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PharmacyOwnerDetailsService implements UserDetailsService {
    @Autowired
    AuthenticationRepository authenticationRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PharmacyOwner owner = authenticationRepository.findPharmacyOwnerByEmail(email);
        if(owner == null){
            System.out.println("User Not Fouund");
            throw new UsernameNotFoundException("User Not Found");
        }
        return new PharmacyOwnerPrincipal(owner);
    }
}
