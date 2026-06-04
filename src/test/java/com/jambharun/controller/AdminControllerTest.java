package com.jambharun.controller;

import com.jambharun.entity.Admin;
import com.jambharun.repository.AdminRepository;
import com.jambharun.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminRepository repository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void loginSuccess() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("1234");

        Admin request = new Admin();
        request.setUsername("admin");
        request.setPassword("1234");

        when(repository.findByUsername("admin")).thenReturn(Optional.of(admin));

        String result = adminController.login(request);

        assertEquals("success", result);
    }

    @Test
    void loginFailedWrongPassword() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("1234");

        Admin request = new Admin();
        request.setUsername("admin");
        request.setPassword("wrong");

        when(repository.findByUsername("admin")).thenReturn(Optional.of(admin));

        String result = adminController.login(request);

        assertEquals("failed", result);
    }

    @Test
    void loginFailedUserNotFound() {
        Admin request = new Admin();
        request.setUsername("admin");
        request.setPassword("1234");

        when(repository.findByUsername("admin")).thenReturn(Optional.empty());

        String result = adminController.login(request);

        assertEquals("failed", result);
    }

    @Test
    void sendOtpSuccess() {
        Admin admin = new Admin();
        admin.setEmail("admin@gmail.com");

        when(repository.findByEmail("admin@gmail.com"))
                .thenReturn(Optional.of(admin));

        String result = adminController.sendOtp(
                Map.of("email", "admin@gmail.com")
        );

        assertEquals("otp-sent", result);
        verify(emailService, times(1))
                .sendOtp(eq("admin@gmail.com"), anyString());
    }

    @Test
    void sendOtpEmailNotFound() {
        when(repository.findByEmail("wrong@gmail.com"))
                .thenReturn(Optional.empty());

        String result = adminController.sendOtp(
                Map.of("email", "wrong@gmail.com")
        );

        assertEquals("email-not-found", result);
        verify(emailService, never()).sendOtp(anyString(), anyString());
    }

    @Test
    void sendOtpError() {
        Admin admin = new Admin();
        admin.setEmail("admin@gmail.com");

        when(repository.findByEmail("admin@gmail.com"))
                .thenReturn(Optional.of(admin));

        doThrow(new RuntimeException("Mail error"))
                .when(emailService)
                .sendOtp(anyString(), anyString());

        String result = adminController.sendOtp(
                Map.of("email", "admin@gmail.com")
        );

        assertEquals("otp-error", result);
    }

    @Test
    void resetPasswordSuccess() {
        ReflectionTestUtils.setField(adminController, "generatedOtp", "123456");

        Admin admin = new Admin();
        admin.setEmail("admin@gmail.com");
        admin.setPassword("oldpass");

        when(repository.findByEmail("admin@gmail.com"))
                .thenReturn(Optional.of(admin));

        String result = adminController.resetPassword(
                Map.of(
                        "email", "admin@gmail.com",
                        "otp", "123456",
                        "newPassword", "newpass"
                )
        );

        assertEquals("password-reset", result);
        assertEquals("newpass", admin.getPassword());

        verify(repository, times(1)).save(admin);
    }

    @Test
    void resetPasswordMissingData() {
        String result = adminController.resetPassword(
                Map.of(
                        "email", "admin@gmail.com",
                        "otp", "123456"
                )
        );

        assertEquals("missing-data", result);
    }

    @Test
    void resetPasswordInvalidOtp() {
        ReflectionTestUtils.setField(adminController, "generatedOtp", "123456");

        String result = adminController.resetPassword(
                Map.of(
                        "email", "admin@gmail.com",
                        "otp", "999999",
                        "newPassword", "newpass"
                )
        );

        assertEquals("invalid-otp", result);
    }

    @Test
    void resetPasswordEmailNotFound() {
        ReflectionTestUtils.setField(adminController, "generatedOtp", "123456");

        when(repository.findByEmail("admin@gmail.com"))
                .thenReturn(Optional.empty());

        String result = adminController.resetPassword(
                Map.of(
                        "email", "admin@gmail.com",
                        "otp", "123456",
                        "newPassword", "newpass"
                )
        );

        assertEquals("email-not-found", result);
    }
}