package com.sahibinden_clone.sahibinden_clone.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditUserRequest {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Email(message = "Geçerli bir email giriniz")
    private String email;   // opsiyonel

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(min = 11, max = 11, message = "Telefon 11 karakter olmalı")
    private String phone;


}
