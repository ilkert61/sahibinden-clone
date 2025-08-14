package com.sahibinden_clone.sahibinden_clone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Users {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 12)
    @NotNull
    @Column(name = "username", nullable = false, length = 12)
    private String username;

    @Size(max = 50)
    @NotNull
    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    @Size(max = 50)
    @NotNull
    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;

    @Size(max = 11)
    @NotNull
    @Column(name = "phone", nullable = false, length = 11)
    private String phone;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Size(max = 6)
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "gender", nullable = false, length = 6)
    private Gender gender;

    @Size(max = 12)
    @NotNull
    @Column(name = "password", nullable = false, length = 12)
    private String password;}




