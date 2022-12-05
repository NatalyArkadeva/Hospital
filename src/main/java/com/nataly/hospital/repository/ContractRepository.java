package com.nataly.hospital.repository;

import com.nataly.hospital.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query(value = "from Contract where patient.id = :id")
    List<Contract> findAllByPatientId(long id);
    Optional<Contract> findByDateAndNumber(LocalDate date, int number);
}
