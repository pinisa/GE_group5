package com.example.canteenbackend.controller;

import com.example.canteenbackend.dto.BookingRequest;
import com.example.canteenbackend.dto.CheckInRequest;
import com.example.canteenbackend.entity.Booking;
import com.example.canteenbackend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        try {
            Booking booking = bookingService.createBooking(request);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/check-in")
    public ResponseEntity<?> checkIn(@RequestBody CheckInRequest request) {
        try {
            Booking booking = bookingService.checkIn(request);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/check-out/{id}")
    public ResponseEntity<?> checkOut(@PathVariable Long id) {
        try {
            Booking booking = bookingService.checkOut(id);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getUserHistory(userId));
    }
}