package com.jambharun.integration;


import com.jambharun.entity.Admin;
import com.jambharun.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class AdminIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminRepository adminRepository;

    @MockitoBean
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setup() {
        adminRepository.deleteAll();

        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("1234");
        admin.setEmail("admin@gmail.com");

        adminRepository.save(admin);
    }

    @Test
    void loginControllerToRepositorySuccess() throws Exception {
        mockMvc.perform(post("/api/admin/login")
                        .contentType("application/json")
                        .content("""
                                {
                                  "username": "admin",
                                  "password": "1234"
                                }
                                """))
                .andExpect(content().string("success"));
    }

    @Test
    void sendOtpControllerToServiceToRepositorySuccess() throws Exception {
        mockMvc.perform(post("/api/admin/send-otp")
                        .contentType("application/json")
                        .content("""
                                {
                                  "email": "admin@gmail.com"
                                }
                                """))
                .andExpect(content().string("otp-sent"));

        verify(javaMailSender).send(any(org.springframework.mail.SimpleMailMessage.class));
    }

    @Test
    void resetPasswordControllerToRepositorySuccess() throws Exception {

        mockMvc.perform(post("/api/admin/send-otp")
                        .contentType("application/json")
                        .content("""
                                {
                                  "email": "admin@gmail.com"
                                }
                                """))
                .andExpect(content().string("otp-sent"));

    }
}