package com.example.canteenbackend.service;

import com.example.canteenbackend.dto.LoginRequest;
import com.example.canteenbackend.entity.User;
import com.example.canteenbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User loginWithKkuMail(LoginRequest request) {
        if (!request.getKkuMail().endsWith("@kku.ac.th")) {
            throw new IllegalArgumentException("ต้องใช้อีเมล @kku.ac.th เท่านั้น");
        }

        return userRepository.findByKkuMail(request.getKkuMail())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setKkuMail(request.getKkuMail());
                    newUser.setName(request.getKkuMail().split("@")[0]);
                    newUser.setRole("STUDENT");
                    return userRepository.save(newUser);
                });
    }
}