package com.example.canteenbackend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // เมื่อผู้ใช้เข้าถึง root URL (/) ให้ Redirect ไปที่ /login.html ทันที
        registry.addRedirectViewController("/", "/login.html");
    }
}