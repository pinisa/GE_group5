package com.example.canteenbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String kkuMail;

    private String name;
    private String studentId;
    private String role;

    public User() {
    }

    public User(Long id, String kkuMail, String name, String studentId, String role) {
        this.id = id;
        this.kkuMail = kkuMail;
        this.name = name;
        this.studentId = studentId;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKkuMail() {
        return kkuMail;
    }

    public void setKkuMail(String kkuMail) {
        this.kkuMail = kkuMail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}