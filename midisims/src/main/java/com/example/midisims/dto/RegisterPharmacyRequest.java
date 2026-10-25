package com.example.midisims.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.parsing.Location;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegisterPharmacyRequest {
    private String pharmacyName;
    private String ownerName;
    private String location;
    private String email;
    private String password;
}
