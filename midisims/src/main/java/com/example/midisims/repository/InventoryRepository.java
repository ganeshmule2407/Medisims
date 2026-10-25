package com.example.midisims.repository;

import com.example.midisims.model.Inventory;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findByPharmacy_PharmacyIdAndMedicine_MedicineId(
            Long pharmacyId,
            Long medicineId
    );

    List<Inventory> findByMedicine_MedicineIdAndTotalQuantityGreaterThan(
            Long medicineId,
            int totalQuantity
    );
}
