package com.example.midisims.controller;

import com.example.midisims.dto.ManualAddStockRequest;
import com.example.midisims.dto.ManualAddStockResponse;
import com.example.midisims.dto.SearchMedicineRequest;
import com.example.midisims.dto.SearchMedicineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.midisims.service.InventoryService;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:5173")
public class InventoryController {
    @Autowired
    InventoryService service;

    @PostMapping("/addStock/manually")
    public ManualAddStockResponse addStockManual(@RequestBody ManualAddStockRequest request,@RequestAttribute("pharmacyId") Long pharmacyId){
        return service.addStockManual(request,pharmacyId);
    }

    @GetMapping("/searchMedicine")
    public SearchMedicineResponse searchMedicine(
            @RequestParam String medicineName,
            @RequestParam String strength) {

        SearchMedicineRequest request = new SearchMedicineRequest();
        request.setMedicineName(medicineName);
        request.setStrength(strength);

        return service.searchMedicine(request);
    }
}
