package com.example.canteenbackend.service;

import com.example.canteenbackend.entity.DiningTable;
import com.example.canteenbackend.repository.DiningTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiningTableService {

    @Autowired
    private DiningTableRepository tableRepository;

    public List<DiningTable> getAllTables() {
        return tableRepository.findAll();
    }

    public double calculateCanteenDensity() {
        long total = tableRepository.count();
        if (total == 0) return 0.0;
        long occupied = tableRepository.countByStatus("OCCUPIED") + tableRepository.countByStatus("RESERVED");
        return ((double) occupied / total) * 100;
    }
}