package com.example.midisims.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchMedicineResponse {
    String medicineName;
    String msg ;
    List<PharmacyStockDTO> pharmacies;
}
