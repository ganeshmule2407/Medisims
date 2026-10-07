package com.example.midisims.dto;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManualAddStockRequest {
    private String medicineName;
    private String generalName;
    private String strength;
    private String batchNumber;
    private LocalDate expiry;
    private String manufacturer;
    private Long quantity;
    private BigDecimal purchasePrice;
    private BigDecimal sellingPrice;
}
