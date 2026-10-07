package com.example.midisims.controller;

import com.example.midisims.dto.CreatePharmacyRequest;
import com.example.midisims.model.Pharmacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.midisims.repository.PharmacyRepository;

@RestController
@RequestMapping("/start")
public class AuthController {

    @Autowired
    PharmacyRepository pharmacyRepository;

    @PostMapping("/createPharmacy")
    public String createPharmacy(@RequestBody CreatePharmacyRequest request){
            Pharmacy pharmacy = new Pharmacy();
            pharmacy.setPharmacyName(request.getPharmacyName());
            pharmacyRepository.save(pharmacy);
            return "Pharmacy Created Successfully";
    }

    @GetMapping("/test")
    public String test() {
        return "Controller is working";
    }
}
