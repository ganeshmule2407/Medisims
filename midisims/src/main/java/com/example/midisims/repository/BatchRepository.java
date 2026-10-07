package com.example.midisims.repository;

import com.example.midisims.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> {
    Optional<Batch> findByInventory_InventoryIdAndBatchNumber(
            Long inventoryId,
            String batchNumber
    );
}
