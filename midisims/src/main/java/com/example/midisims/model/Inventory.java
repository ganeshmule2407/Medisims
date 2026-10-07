package com.example.midisims.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inventory {
    @Id
    @GeneratedValue
    private Long inventoryId;
    @ManyToOne
    private Medicine medicine;
    @ManyToOne
    private Pharmacy pharmacy;
    private Long totalQuantity;
}
