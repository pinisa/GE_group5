package com.example.canteenbackend.repository;

import com.example.canteenbackend.entity.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {
    Optional<DiningTable> findByTableNo(String tableNo);
    long countByStatus(String status);
}