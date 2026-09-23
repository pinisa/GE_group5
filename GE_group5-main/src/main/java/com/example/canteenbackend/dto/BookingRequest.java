package com.example.canteenbackend.dto;

public class BookingRequest {
    private Long userId;
    private String tableNo;
    private String timeSlot;

    public BookingRequest() {
    }

    public BookingRequest(Long userId, String tableNo, String timeSlot) {
        this.userId = userId;
        this.tableNo = tableNo;
        this.timeSlot = timeSlot;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}