package com.example.canteenbackend.config;

import com.example.canteenbackend.entity.DiningTable;
import com.example.canteenbackend.repository.DiningTableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DiningTableRepository tableRepository;

    public DataInitializer(DiningTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (tableRepository.count() == 0) {
            List<DiningTable> tables = new ArrayList<>();

            // ==================== ชั้น 1 ====================
            // โต๊ะโซน A (A1 - A5)
            for (int i = 1; i <= 5; i++) tables.add(new DiningTable(null, "A" + i, "AVAILABLE", 4));
            
            // โต๊ะโซน B (B1 - B3)
            for (int i = 1; i <= 3; i++) tables.add(new DiningTable(null, "B" + i, "AVAILABLE", 4));
            
            // โต๊ะโซน C (C1 - C3)
            for (int i = 1; i <= 3; i++) tables.add(new DiningTable(null, "C" + i, "AVAILABLE", 4));
            
            // โต๊ะโซน D (D1 - D5)
            for (int i = 1; i <= 5; i++) tables.add(new DiningTable(null, "D" + i, "AVAILABLE", 4));
            
            // โต๊ะโซน E (E1 - E7)
            for (int i = 1; i <= 7; i++) tables.add(new DiningTable(null, "E" + i, "AVAILABLE", 4));
            
            // โต๊ะริมโซน S (S1 - S4)
            for (int i = 1; i <= 4; i++) tables.add(new DiningTable(null, "S" + i, "AVAILABLE", 2));

            // โต๊ะเล็กตรงกลาง (1-14 มี 2 ชุด แบ่งเป็นฝั่งซ้ายและขวา)
            for (int i = 1; i <= 14; i++) tables.add(new DiningTable(null, "T1-" + i, "AVAILABLE", 1));
            for (int i = 1; i <= 14; i++) tables.add(new DiningTable(null, "T2-" + i, "AVAILABLE", 1));

            // ==================== ชั้นใต้ดิน ====================
            // โต๊ะโซน U1 (U11 - U16)
            for (int i = 1; i <= 6; i++) tables.add(new DiningTable(null, "U1" + i, "AVAILABLE", 4));
            
            // โต๊ะโซน U2 (U21 - U26)
            for (int i = 1; i <= 6; i++) tables.add(new DiningTable(null, "U2" + i, "AVAILABLE", 4));
            
            // โต๊ะโซน U3 (U31 - U36)
            for (int i = 1; i <= 6; i++) tables.add(new DiningTable(null, "U3" + i, "AVAILABLE", 4));
            
            // โต๊ะโซน U4 (U41 - U46)
            for (int i = 1; i <= 6; i++) tables.add(new DiningTable(null, "U4" + i, "AVAILABLE", 4));
            
            // โต๊ะโซน U5 (U51 - U54)
            for (int i = 1; i <= 4; i++) tables.add(new DiningTable(null, "U5" + i, "AVAILABLE", 4));

            // บันทึกโต๊ะทั้งหมดลงฐานข้อมูล
            tableRepository.saveAll(tables);
            System.out.println("✅ สร้างข้อมูลผังโต๊ะอาหารสำเร็จทั้งหมด " + tables.size() + " โต๊ะ!");
        }
    }
}