package com.sahibinden_clone.sahibinden_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String user; // username veya email
    private String pass; // şifre
}
