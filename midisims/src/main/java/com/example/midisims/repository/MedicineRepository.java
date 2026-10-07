package com.example.midisims.repository;

import com.example.midisims.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Long> {
    Optional<Medicine> findByMedicineNameAndStrength(
            String medicineName,
            String strength
    );
}
