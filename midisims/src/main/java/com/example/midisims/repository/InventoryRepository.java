package com.example.midisims.repository;

import com.example.midisims.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findByPharmacy_PharmacyIdAndMedicine_MedicineId(
            Long PharmacyId,
            Long MedicineId
    );
}
