package com.example.canteenbackend.dto;

public class CheckInRequest {
    private Long bookingId;
    private String tableNo;

    public CheckInRequest() {
    }

    public CheckInRequest(Long bookingId, String tableNo) {
        this.bookingId = bookingId;
        this.tableNo = tableNo;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }
}