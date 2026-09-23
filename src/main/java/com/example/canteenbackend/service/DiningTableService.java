package com.example.canteenbackend.service;

import com.example.canteenbackend.entity.DiningTable;
import com.example.canteenbackend.repository.DiningTableRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
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

    // 1. ฟังก์ชันสร้างรูปภาพ QR Code (PNG) สำหรับพิมพ์ติดแต่ละโต๊ะ
    public byte[] generateTableQRCode(Long tableId) throws Exception {
        DiningTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("ไม่พบข้อมูลโต๊ะ ID: " + tableId));

        // ลิงก์สแกนที่จะซ่อนอยู่ใน QR Code
        String scanUrl = "http://localhost:8080/api/tables/" + table.getId() + "/scan";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(scanUrl, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    // 2. ฟังก์ชันเมื่อคนสแกน QR Code -> เปลี่ยนสถานะเป็น OCCUPIED (ไม่ว่าง)
    public String scanAndCheckIn(Long tableId) {
        DiningTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("ไม่พบข้อมูลโต๊ะ ID: " + tableId));

        if ("OCCUPIED".equalsIgnoreCase(table.getStatus())) {
            return "โต๊ะ " + table.getTableNo() + " มีผู้ใช้งาน (ไม่ว่าง) อยู่แล้วในขณะนี้";
        }

        table.setStatus("OCCUPIED");
        tableRepository.save(table);

        return "เช็กอินสำเร็จ! โต๊ะ " + table.getTableNo() + " เปลี่ยนสถานะเป็น 'ไม่ว่าง (OCCUPIED)' เรียบร้อยแล้ว";
    }
}