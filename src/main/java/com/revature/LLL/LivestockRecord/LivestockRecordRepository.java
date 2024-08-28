package com.revature.LLL.LivestockRecord;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LivestockRecordRepository extends JpaRepository<LivestockRecord, Integer> {

    Optional<LivestockRecord> findAllByEntryId(int entryId);

    // jsonb_extract_path_text is a PostgreSQL function that extracts a text value from a JSON object
    @Query(value = "SELECT * FROM livestock WHERE jsonb_extract_path_text(patient_identification::jsonb, 'owner_info', 'userId')::INTEGER = :userId", nativeQuery = true)
    List<LivestockRecord> findAllByPatientIdentificationOwnerInfoUserId(@Param("userId") int userId);

    @Query(value = "SELECT * FROM livestock WHERE jsonb_extract_path_text(patient_identification::jsonb, 'animal_id')::INTEGER = :animalId", nativeQuery = true)
    Optional<LivestockRecord> findAllByPatientIdentificationAnimalId(@Param("animalId") int animalId);

    @Query(value = "SELECT * FROM livestock WHERE jsonb_extract_path_text(vet_record::jsonb, 'vet_details', 'userId')::INTEGER = :userId", nativeQuery = true)
    List<LivestockRecord> findAllByVetRecordVetDetailsUserId(@Param("userId") int userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO livestock (patient_identification, vet_record, medical_history, condition, plan, health, notes) VALUES (CAST(:patientIdentification AS jsonb), CAST(:vetRecord AS jsonb), CAST(:medicalHistory AS jsonb), CAST(:condition AS jsonb), CAST(:plan AS jsonb), CAST(:health AS jsonb), CAST(:notes AS jsonb))", nativeQuery = true)
    void insertLivestockRecord(@Param("patientIdentification") String patientIdentification,
            @Param("vetRecord") String vetRecord,
            @Param("medicalHistory") String medicalHistory,
            @Param("condition") String condition,
            @Param("plan") String plan,
            @Param("health") String health,
            @Param("notes") String notes);

}
