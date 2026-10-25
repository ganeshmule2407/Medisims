package com.example.midisims.service;
import com.example.midisims.dto.LoginPharmacyRequest;
import com.example.midisims.dto.RegisterPharmacyRequest;
import com.example.midisims.model.Pharmacy;
import com.example.midisims.model.PharmacyOwner;
import com.example.midisims.repository.AuthenticationRepository;
import com.example.midisims.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    PharmacyRepository pharmacyRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;

    public String register(RegisterPharmacyRequest request){
        PharmacyOwner owner=authenticationRepository.findPharmacyOwnerByEmail(request.getEmail());
        if(owner!=null)return "Email already Registered";

        Pharmacy pharmacy=new Pharmacy();
        pharmacy.setPharmacyName(request.getPharmacyName());
        pharmacy.setLocation(request.getLocation());
        pharmacyRepository.save(pharmacy);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        PharmacyOwner pharmacyOwner = new PharmacyOwner();
        pharmacyOwner.setPharmacy(pharmacy);
        pharmacyOwner.setPassword(encoder.encode(request.getPassword()));
        pharmacyOwner.setEmail(request.getEmail());
        authenticationRepository.save(pharmacyOwner);

        return "Registration Successful";
    }

    public String login(LoginPharmacyRequest request) {

        // 1. Verify the email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Find the authenticated owner's record
        PharmacyOwner owner =
                authenticationRepository.findPharmacyOwnerByEmail(
                        request.getEmail()
                );

        if (owner == null || owner.getPharmacy() == null) {
            throw new RuntimeException(
                    "Pharmacy owner or associated pharmacy not found"
            );
        }

        // 3. Obtain the pharmacy ID from the database
        Long pharmacyId = owner.getPharmacy().getPharmacyId();

        // 4. Generate a token containing email + pharmacy ID
        return jwtService.generateToken(
                owner.getEmail(),
                pharmacyId
        );
    }
}
