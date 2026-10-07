package com.example.midisims.controller;

import com.example.midisims.dto.ManualAddStockRequest;
import com.example.midisims.dto.ManualAddStockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.midisims.service.InventoryService;

@RestController
@RequestMapping("/{pharmacyId}/addStock")
public class InventoryController {
    @Autowired
    InventoryService service;

    @PostMapping("/manually")
    public ManualAddStockResponse addStockManual(@RequestBody ManualAddStockRequest request,@PathVariable Long pharmacyId){
        return service.addStockManual(request,pharmacyId);
    }

}
