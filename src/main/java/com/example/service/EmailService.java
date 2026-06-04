package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("dhirajj8048@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Gram Panchayat Admin Password Reset OTP");
        message.setText(
                "Hello Admin,\n\n" +
                        "Your OTP for password reset is: " + otp + "\n\n" +
                        "Do not share this OTP with anyone.\n\n" +
                        "Gram Panchayat Jambharun Tanda"
        );

        mailSender.send(message);
    }
}