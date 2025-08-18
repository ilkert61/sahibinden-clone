package com.sahibinden_clone.sahibinden_clone.dto;

import jakarta.validation.constraints.NotBlank;

public class DeleteUserRequest {
    @NotBlank
    private String password;

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
