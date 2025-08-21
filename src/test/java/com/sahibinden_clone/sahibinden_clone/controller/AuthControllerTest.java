package com.sahibinden_clone.sahibinden_clone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahibinden_clone.sahibinden_clone.dto.LoginRequest;
import com.sahibinden_clone.sahibinden_clone.dto.LoginResponse;
import com.sahibinden_clone.sahibinden_clone.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_shouldReturnSuccess() throws Exception {
        UUID userId = UUID.randomUUID();
        given(authService.login(any(LoginRequest.class)))
                .willReturn(new LoginResponse(true, "Giriş başarılı", userId));

        LoginRequest req = new LoginRequest();
        req.setUser("testuser");
        req.setPass("123456");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", containsString("Giriş başarılı")))
                .andExpect(jsonPath("$.userId", is(userId.toString())));
    }

    @Test
    void login_shouldReturnFailure() throws Exception {
        given(authService.login(any(LoginRequest.class)))
                .willReturn(new LoginResponse(false, "Kullanıcı bulunamadı", null));

        LoginRequest req = new LoginRequest();
        req.setUser("nouser");
        req.setPass("123456");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", containsString("Kullanıcı bulunamadı")))
                .andExpect(jsonPath("$.userId").doesNotExist());
    }
}
