package com.example.midisims.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class ManualAddStockResponse {
    private String msg;
    private String medicineName;
    private String batchNumber;
    private Long QuantityAdded;
    private Long totalQuantity;
}

