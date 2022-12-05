package com.nataly.hospital.repository;

import com.nataly.hospital.entity.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    Optional<PriceList> findByTitle(String title);
}
