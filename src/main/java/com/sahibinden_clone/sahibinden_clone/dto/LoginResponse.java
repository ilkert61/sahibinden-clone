package com.sahibinden_clone.sahibinden_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private boolean success;
    private String message;
    private UUID userId;
    private String username;
}
