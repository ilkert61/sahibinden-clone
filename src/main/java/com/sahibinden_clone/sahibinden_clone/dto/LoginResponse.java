package com.sahibinden_clone.sahibinden_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LoginResponse {
    private boolean success;
    private String message;
    private UUID userId; }