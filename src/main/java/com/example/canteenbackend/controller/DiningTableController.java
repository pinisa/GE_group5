package com.example.canteenbackend.controller;

import com.example.canteenbackend.entity.DiningTable;
import com.example.canteenbackend.service.DiningTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tables")
@CrossOrigin(origins = "*")
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

    // 1. API ดึงรูปภาพ QR Code ของแต่ละโต๊ะ (ส่งคืนเป็นไฟล์ PNG สำหรับเปิดดู / สั่งพิมพ์)
    @GetMapping(value = "/{id}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTableQRCode(@PathVariable Long id) {
        try {
            byte[] qrImage = tableService.generateTableQRCode(id);
            return ResponseEntity.ok(qrImage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 2. API เมื่อสแกน QR Code เช็กอิน -> เปลี่ยนสถานะโต๊ะเป็น OCCUPIED (ไม่ว่าง)
    @GetMapping("/{id}/scan")
    public ResponseEntity<String> scanTable(@PathVariable Long id) {
        try {
            String resultMessage = tableService.scanAndCheckIn(id);
            return ResponseEntity.ok(resultMessage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. API สำหรับแสดง QR Code ของทุกโต๊ะในหน้าเดียว พร้อมพิมพ์ / Save PDF
    @GetMapping(value = "/print-qrcodes", produces = MediaType.TEXT_HTML_VALUE)
    public String printAllQRCodes() {
        List<DiningTable> tables = tableService.getAllTables();
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html><html><head><title>QR Codes ทั้งหมดสำหรับพิมพ์ติดโต๊ะ</title>");
        html.append("<style>");
        html.append("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; padding: 20px; background: #f4f6f8; color: #333; }");
        html.append(".header { text-align: center; margin-bottom: 30px; }");
        html.append(".btn-print { font-size: 18px; font-weight: bold; padding: 14px 28px; background: #2e7d32; color: white; border: none; border-radius: 8px; cursor: pointer; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }");
        html.append(".btn-print:hover { background: #1b5e20; }");
        html.append(".grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(190px, 1fr)); gap: 20px; }");
        html.append(".card { background: white; border: 2px solid #2e7d32; border-radius: 12px; padding: 15px; text-align: center; page-break-inside: avoid; box-shadow: 0 2px 5px rgba(0,0,0,0.05); }");
        html.append(".table-no { font-size: 28px; font-weight: bold; color: #1b5e20; margin-bottom: 8px; }");
        html.append(".qr-img { width: 140px; height: 140px; border-radius: 6px; }");
        html.append(".capacity { font-size: 13px; color: #666; margin-top: 8px; font-weight: 500; }");
        html.append(".instruction { font-size: 11px; color: #888; margin-top: 4px; }");
        html.append("@media print { .no-print { display: none; } body { background: white; padding: 0; } .grid { gap: 12px; } .card { border: 1.5px solid #000; } }");
        html.append("</style></head><body>");
        
        html.append("<div class='header no-print'>");
        html.append("<h1>🖨️ แผ่นพิมพ์ QR Code สำหรับติดโต๊ะอาหาร (รวม ").append(tables.size()).append(" โต๊ะ)</h1>");
        html.append("<p style='color:#666;'>สามารถสั่งพิมพ์ลงกระดาษ หรือบันทึกเป็นไฟล์ PDF นำไปตัดแปะติดโต๊ะได้ทันที</p>");
        html.append("<button class='btn-print' onclick='window.print()'>🖨️ กดที่นี่เพื่อพิมพ์ / บันทึกเป็น PDF</button>");
        html.append("</div>");
        
        html.append("<div class='grid'>");
        for (DiningTable table : tables) {
            html.append("<div class='card'>");
            html.append("<div class='table-no'>โต๊ะ ").append(table.getTableNo()).append("</div>");
            html.append("<img class='qr-img' src='/api/tables/").append(table.getId()).append("/qrcode' alt='QR Code'>");
            html.append("<div class='capacity'>รองรับ: ").append(table.getCapacity()).append(" ที่นั่ง</div>");
            html.append("<div class='instruction'>สแกนเพื่อเช็กอินเข้าใช้งาน</div>");
            html.append("</div>");
        }
        html.append("</div></body></html>");
        
        return html.toString();
    }
}