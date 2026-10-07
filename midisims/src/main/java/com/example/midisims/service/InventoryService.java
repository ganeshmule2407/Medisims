package com.example.midisims.service;

import com.example.midisims.dto.ManualAddStockRequest;
import com.example.midisims.dto.ManualAddStockResponse;
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
    @Autowired
    ManualAddStockResponse manualAddStockResponse;

    public ManualAddStockResponse addStockManual(ManualAddStockRequest request,Long pharmacyId) {
        Medicine medicine;
        Inventory inventory;
        Batch batch;
        //Find Pharmacy
        Pharmacy pharmacy=pharmacyRepository.findByPharmacyId(pharmacyId);
        if(pharmacy==null)throw new RuntimeException("Pharmacy Not found");
        //Find Medicine
        Optional<Medicine>existingMedicine=medicineRepository.findByMedicineNameAndStrength(
                request.getMedicineName(),
                request.getStrength()
        );

        medicine = existingMedicine.orElseGet(() -> createMedicine(request));
//        if(existingMedicine.isPresent()){  as same as above line
//            medicine= existingMedicine.get();
//        }else{
//            medicine=createMedicine(request);
//        }
        //Find Inventory
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

        //Find Batch
        Optional<Batch>existingBatch=batchRepository.findByInventory_InventoryIdAndBatchNumber(
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

        manualAddStockResponse.setBatchNumber(batch.getBatchNumber());
        manualAddStockResponse.setMedicineName(medicine.getMedicineName());
        manualAddStockResponse.setTotalQuantity(inventory.getTotalQuantity());
        manualAddStockResponse.setMsg("Stock added successfully");
        manualAddStockResponse.setQuantityAdded(request.getQuantity());
        return manualAddStockResponse;
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
