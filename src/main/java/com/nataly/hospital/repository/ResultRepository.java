package com.nataly.hospital.repository;

import com.nataly.hospital.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query(value = "from Result where user.id = :id")
    List<Result> findAll(long id);
}
