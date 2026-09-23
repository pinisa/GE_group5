package com.example.canteenbackend.dto;

public class LoginRequest {
    private String kkuMail;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String kkuMail, String password) {
        this.kkuMail = kkuMail;
        this.password = password;
    }

    public String getKkuMail() {
        return kkuMail;
    }

    public void setKkuMail(String kkuMail) {
        this.kkuMail = kkuMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}