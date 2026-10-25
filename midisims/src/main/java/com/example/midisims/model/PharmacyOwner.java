package com.example.midisims.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PharmacyOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pharmacyOwner_id")
    private Long pharmacyOwnerId;
    private String email;
    private String password;
    @OneToOne
    @JoinColumn(name="pharmacy_id")
    private Pharmacy pharmacy;
}
