package com.example.midisims.repository;

import com.example.midisims.model.PharmacyOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<PharmacyOwner,Long> {
    PharmacyOwner findPharmacyOwnerByEmail(String Email);
}
