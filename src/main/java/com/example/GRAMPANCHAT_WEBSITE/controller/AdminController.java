package com.example.GRAMPANCHAT_WEBSITE.controller;

import com.example.GRAMPANCHAT_WEBSITE.entity.Admin;
import com.example.GRAMPANCHAT_WEBSITE.repository.AdminRepository;
import com.example.GRAMPANCHAT_WEBSITE.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminRepository repository;

    @Autowired
    private EmailService emailService;

    private String generatedOtp = "";

    @PostMapping("/login")
    public String login(@RequestBody Admin request) {

        Optional<Admin> admin =
                repository.findByUsername(request.getUsername());

        if (admin.isPresent()) {

            if (admin.get().getPassword()
                    .equals(request.getPassword())) {

                return "success";
            }
        }

        return "failed";
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody Map<String, String> request) {

        try {
            String email = request.get("email");

            Optional<Admin> admin = repository.findByEmail(email);

            if (admin.isEmpty()) {
                return "email-not-found";
            }

            generatedOtp = String.valueOf(100000 + new Random().nextInt(900000));

            emailService.sendOtp(email, generatedOtp);

            return "otp-sent";

        } catch (Exception e) {
            e.printStackTrace();
            return "otp-error";
        }
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (email == null || otp == null || newPassword == null) {
            return "missing-data";
        }

        if (!generatedOtp.equals(otp)) {
            return "invalid-otp";
        }

        Optional<Admin> admin = repository.findByEmail(email);

        if (admin.isEmpty()) {
            return "email-not-found";
        }

        Admin existingAdmin = admin.get();

        existingAdmin.setPassword(newPassword);

        repository.save(existingAdmin);

        generatedOtp = "";

        return "password-reset";
    }
}