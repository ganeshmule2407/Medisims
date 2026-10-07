package com.example.midisims.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Batch {
    @Id
    @GeneratedValue
    private Long batchId;
    private String batchNumber;
    @ManyToOne
    private Inventory inventory;
    private LocalDate expiry;
    private Long totalQuantity;
    private BigDecimal sellingPrice;
    private BigDecimal purchasePrice;
}
