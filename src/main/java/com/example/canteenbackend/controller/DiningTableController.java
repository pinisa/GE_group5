package com.example.canteenbackend.controller;

import com.example.canteenbackend.entity.DiningTable;
import com.example.canteenbackend.service.DiningTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tables")
public class DiningTableController {

    @Autowired
    private DiningTableService tableService;

    @GetMapping
    public ResponseEntity<List<DiningTable>> getAllTables() {
        return ResponseEntity.ok(tableService.getAllTables());
    }

    @GetMapping("/density")
    public ResponseEntity<?> getDensity() {
        double density = tableService.calculateCanteenDensity();
        return ResponseEntity.ok(Map.of("densityPercentage", density));
    }
}