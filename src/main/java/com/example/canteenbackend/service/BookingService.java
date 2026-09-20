package com.example.canteenbackend.service;

import com.example.canteenbackend.dto.BookingRequest;
import com.example.canteenbackend.dto.CheckInRequest;
import com.example.canteenbackend.entity.Booking;
import com.example.canteenbackend.entity.DiningTable;
import com.example.canteenbackend.entity.User;
import com.example.canteenbackend.repository.BookingRepository;
import com.example.canteenbackend.repository.DiningTableRepository;
import com.example.canteenbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private DiningTableRepository tableRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(BookingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("ไม่พบผู้ใช้งาน"));

        DiningTable table = tableRepository.findByTableNo(request.getTableNo())
                .orElseThrow(() -> new RuntimeException("ไม่พบโต๊ะที่ระบุ"));

        if (!"AVAILABLE".equals(table.getStatus())) {
            throw new RuntimeException("โต๊ะนี้ไม่ว่างในขณะนี้");
        }

        table.setStatus("RESERVED");
        tableRepository.save(table);

        Booking booking = new Booking();
        booking.setBookingCode("BK-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase());
        booking.setUser(user);
        booking.setDiningTable(table);
        booking.setTimeSlot(request.getTimeSlot());
        booking.setStatus("PENDING");
        booking.setBookingTime(LocalDateTime.now());
        booking.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        return bookingRepository.save(booking);
    }

    public Booking checkIn(CheckInRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("ไม่พบข้อมูลการจอง"));

        if (!booking.getDiningTable().getTableNo().equalsIgnoreCase(request.getTableNo())) {
            throw new RuntimeException("สแกน QR Code ไม่ตรงกับโต๊ะที่จอง");
        }

        booking.setStatus("CHECKED_IN");
        DiningTable table = booking.getDiningTable();
        table.setStatus("OCCUPIED");
        tableRepository.save(table);

        return bookingRepository.save(booking);
    }

    public Booking checkOut(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("ไม่พบข้อมูลการจอง"));

        booking.setStatus("COMPLETED");
        DiningTable table = booking.getDiningTable();
        table.setStatus("AVAILABLE");
        tableRepository.save(table);

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserHistory(Long userId) {
        return bookingRepository.findByUserIdOrderByBookingTimeDesc(userId);
    }
}