package com.example.midisims.model;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Long batchId;
    private String batchNumber;
    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    private LocalDate expiry;
    private Long totalQuantity;
    private BigDecimal sellingPrice;
    private BigDecimal purchasePrice;
}
