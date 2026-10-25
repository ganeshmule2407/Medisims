package com.example.midisims.service;

import com.example.midisims.dto.*;
import com.example.midisims.model.Batch;
import com.example.midisims.model.Inventory;
import com.example.midisims.model.Medicine;
import com.example.midisims.model.Pharmacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.midisims.repository.BatchRepository;
import com.example.midisims.repository.InventoryRepository;
import com.example.midisims.repository.MedicineRepository;
import com.example.midisims.repository.PharmacyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class InventoryService {
    @Autowired
    PharmacyRepository pharmacyRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    MedicineRepository medicineRepository;

    public ManualAddStockResponse addStockManual(ManualAddStockRequest request,Long pharmacyId) {
        Inventory inventory;
        Batch batch;

        Pharmacy pharmacy=pharmacyRepository.findByPharmacyId(pharmacyId);//Find Pharmacy
        if(pharmacy==null)throw new RuntimeException("Pharmacy Not found");

        Optional<Medicine>existingMedicine=medicineRepository.findByMedicineNameAndStrength(
                request.getMedicineName(),
                request.getStrength()
        );

        Medicine medicine = existingMedicine.orElseGet(() -> createMedicine(request));   //Find Inventory

        Optional<Inventory>existingInventory=inventoryRepository.findByPharmacy_PharmacyIdAndMedicine_MedicineId(
                pharmacyId,
                medicine.getMedicineId()
        );

        if(existingInventory.isPresent()){
            Long updatedQuantity=existingInventory.get().getTotalQuantity()+request.getQuantity();
            existingInventory.get().setTotalQuantity(updatedQuantity);
            inventoryRepository.save(existingInventory.get());
            inventory=existingInventory.get();
        }
        else inventory=createInventory(request,medicine,pharmacyId);

        Optional<Batch>existingBatch=batchRepository.findByInventory_InventoryIdAndBatchNumber(//Find Batch
                inventory.getInventoryId(),
                request.getBatchNumber()
        );

        if(existingBatch.isPresent()) {
            Long updatedQuantity=existingBatch.get().getTotalQuantity()+request.getQuantity();
            existingBatch.get().setTotalQuantity(updatedQuantity);
            batchRepository.save(existingBatch.get());
            batch=existingBatch.get();
        }
        else batch=createBatch(request,inventory);

        return new ManualAddStockResponse(
                "Stock added successfully",
                medicine.getMedicineName(),
                batch.getBatchNumber(),
                request.getQuantity(),
                inventory.getTotalQuantity());
    }

    public SearchMedicineResponse searchMedicine(SearchMedicineRequest request) {
        List<PharmacyStockDTO> pharmacies=new ArrayList<>();
         Optional<Medicine> medicine= medicineRepository.findByMedicineNameAndStrength(request.getMedicineName(), request.getStrength());
         if(medicine.isEmpty()){
             return new SearchMedicineResponse(request.getMedicineName(), "Medicine Not Found",pharmacies);
         }
         List<Inventory> inventories = inventoryRepository.findByMedicine_MedicineIdAndTotalQuantityGreaterThan(medicine.get().getMedicineId(), 0);
         if(inventories.isEmpty()){
             return new SearchMedicineResponse(request.getMedicineName(),"Medicine Not Available",pharmacies);
         }
         for(Inventory inventory : inventories){
             String pharmacyName=inventory.getPharmacy().getPharmacyName();
             pharmacies.add(new PharmacyStockDTO(pharmacyName,inventory.getTotalQuantity()));
         }
         return new SearchMedicineResponse(
                 request.getMedicineName(),
                 "stock found",
                 pharmacies
         );
    }

    public Medicine createMedicine(ManualAddStockRequest request){
        Medicine medicine=new Medicine();
        medicine.setMedicineName(request.getMedicineName());
        medicine.setGenericName(request.getGeneralName());
        medicine.setStrength(request.getStrength());
        medicine.setManufacturer(request.getManufacturer());
        return medicineRepository.save(medicine);
    }

    public Inventory createInventory(ManualAddStockRequest request,Medicine medicine,Long pharmacyId){
        Inventory inventory=new Inventory();
        inventory.setPharmacy(pharmacyRepository.findByPharmacyId(pharmacyId));
        inventory.setMedicine(medicine);
        inventory.setTotalQuantity(request.getQuantity());
        return inventoryRepository.save(inventory);
    }

    public Batch createBatch(ManualAddStockRequest request,Inventory inventory){
        Batch batch =new Batch();
        batch.setInventory(inventory);
        batch.setBatchNumber(request.getBatchNumber());
        batch.setTotalQuantity(request.getQuantity());
        batch.setExpiry(request.getExpiry());
        batch.setSellingPrice(request.getSellingPrice());
        batch.setPurchasePrice(request.getPurchasePrice());
        return batchRepository.save(batch);
    }
}
