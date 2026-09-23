package com.example.canteenbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dining_tables")
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tableNo;

    @Column(nullable = false)
    private String status; // AVAILABLE, RESERVED, OCCUPIED

    private Integer capacity;

    public DiningTable() {
    }

    public DiningTable(Long id, String tableNo, String status, Integer capacity) {
        this.id = id;
        this.tableNo = tableNo;
        this.status = status;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}