package com.sahibinden_clone.sahibinden_clone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteUserRequest {
    @NotBlank
    private String password;

}
